package com.mymall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by HUA on 2018/5/28.
 * <p>
 * 使用Guava构建一个缓存工具类
 */
public class TokenCache {

    private final static Logger logger = LoggerFactory.getLogger(TokenCache.class);

    public static final String TOKEN_PERFIX = "token_";

    private static LoadingCache<String, String> localCache = CacheBuilder.newBuilder()
            .initialCapacity(1000) //初始化缓存并设置初始化容量
            .maximumSize(10000) //缓存最大容量,超过设定的容量时,使用LRU算法移除缓存项
            .expireAfterAccess(12, TimeUnit.HOURS) //设置缓存有效期
            .build(new CacheLoader<String, String>() {
                //默认的数据加载实现，当调用get取值时，如果key没有对应的值，就调用这个方法进行加载
                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });

    public static void setKey(String key, String value) {
        localCache.put(key, value);
    }

    public static String getKey(String key) {
        String value = null;
        try {
            value = localCache.get(key);
            if ("null".equals(value)) {
                return null;
            }
            return value;
        } catch (Exception e) {
            logger.error("localCache get error", e);
        }

        return null;
    }

}
