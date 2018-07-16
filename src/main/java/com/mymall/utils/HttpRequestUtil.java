package com.mymall.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;


/**
 * @author HUA
 */
public class HttpRequestUtil {

    private static final Integer SUCCESS_CODE = 200;

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

    private static CloseableHttpClient httpClient;

    static {
        PoolingHttpClientConnectionManager httpPool = new PoolingHttpClientConnectionManager();
        httpPool.setMaxTotal(100);
        httpPool.setDefaultMaxPerRoute(20);
        httpPool.setDefaultMaxPerRoute(50);
        httpClient = HttpClients.custom().setConnectionManager(httpPool).build();
    }

    public static String sendGet(String url, Map<String, Object> params) throws Exception {
        if (null != params) {
            StringBuilder stringBuilder = new StringBuilder();
            String key;
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                key = entry.getKey();
                Object val = entry.getValue();
                if (val instanceof List) {
                    List v = (List) val;
                    for (Object o : v) {
                        stringBuilder.append(key).append("=").append(o.toString()).append("&");
                    }
                } else {
                    stringBuilder.append(key).append("=").append(val.toString()).append("&");
                }
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            url = url + "?" + stringBuilder.toString();
        }
        return HttpRequestUtil.sendGet(url);
    }


    public static String sendGet(String url) throws Exception {

        logger.info("sendGet >>> url:{}", url);

        CloseableHttpResponse response = null;
        BufferedReader in = null;
        String result = "";
        try {
            HttpGet httpGet = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000)
                    .setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
            httpGet.setConfig(requestConfig);
            httpGet.setConfig(requestConfig);
            httpGet.addHeader("Content-type", "application/json; charset=utf-8");
            httpGet.setHeader("Accept", "application/json");
            response = httpClient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() != SUCCESS_CODE) {
                String errorLog = "请求失败，errorCode:" + response.getStatusLine().getStatusCode();
                logger.info(errorLog);
                throw new Exception(url + errorLog);
            }

            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPost(String url, String jsonParams) throws Exception {

        logger.info("sendGet >>> url:{},paramJson:{}", url, jsonParams);

        CloseableHttpResponse response = null;
        BufferedReader in = null;
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000)
                    .setConnectionRequestTimeout(30000).setSocketTimeout(30000).build();
            httpPost.setConfig(requestConfig);
            httpPost.setConfig(requestConfig);
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setEntity(new StringEntity(jsonParams, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() != SUCCESS_CODE) {
                String errorLog = "请求失败，errorCode:" + response.getStatusLine().getStatusCode();
                logger.info(errorLog);
                throw new Exception(url + errorLog);
            }

            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
