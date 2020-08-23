package com.example.iotmqtt.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(15000).setConnectTimeout(15000)
            .setConnectionRequestTimeout(15000).build();

    public static String sendHttpGet(String url) {
        String body = HttpRequest.get(url)
                .execute()
                .body();
        return body;
    }
    /**
     * 发送 post请求
     * @param httpUrl 地址
     * @param maps 参数
     */
    public static String sendHttpPost(String httpUrl, Map<String,Object> maps) throws Exception{
        String body = HttpRequest.post(httpUrl)
                .body(JSONUtil.toJsonStr(maps))
                .execute()
                .body();
        return body;
    }
    /**
     * 发送 post请求
     * @param httpUrl 地址
     * @param maps 参数
     */
    public static String sendHttpPost(String httpUrl,Map<String,String> header, Map<String,Object> maps) throws Exception{
        String body = HttpRequest.post(httpUrl)
                .headerMap(header,true)
                .body(JSONUtil.toJsonStr(maps))
                .execute()
                .body();
        return body;
    }
    /**
     * 发送 post请求
     * @param httpUrl 地址
     */
    public static String sendHttpPost(String httpUrl, Map<String,String> header, Object object) throws Exception{
        String body = HttpRequest.post(httpUrl)
                .headerMap(header,true)
                .body(JSONUtil.toJsonStr(object))
                .execute()
                .body();
        return body;
    }
    /**
     * 发送 post请求
     * @param httpUrl 地址
     */
    public static String sendHttpPost(String httpUrl, Object object) throws Exception{
        String body = HttpRequest.post(httpUrl)
                .body(JSONUtil.toJsonStr(object))
                .execute()
                .body();
        return body;
    }

    /**
     * 发送 post请求
     * @param httpUrl 地址
     */
    public static String sendHttpPost(String httpUrl, List<Map<String,Object>> list) throws Exception{
        String body = HttpRequest.post(httpUrl)
                .body(JSONUtil.toJsonStr(list))
                .execute()
                .body();
        return body;
    }

    /**
     * 发送Get请求Https
     * @param httpGet
     * @return
     */
    public static String sendHttpsGet(HttpGet httpGet) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.load(new URL(httpGet.getURI().toString()));
            DefaultHostnameVerifier hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher);
            httpClient = HttpClients.custom().setSSLHostnameVerifier(hostnameVerifier).build();
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseContent;
    }
}



