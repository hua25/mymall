package com.mymall.utils;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class HttpRequestUtilTest {

    @Test
    public void sendGet() throws Exception {
//        String url = "https://blackhole.m.jd.com/getinfo";
        String url = "https://search.jd.com/shop_new.php";
        Map<String, Object> param = Maps.newHashMap();
        param.put("ids", 1000004259);
        String result = HttpRequestUtil.sendGet(url,param);
        System.out.println(result);
    }

    @Test
    public void sendPost() {
    }
}