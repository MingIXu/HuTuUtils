package com.greentree.utils;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * OKHttp封装工具类
 *
 * @author hutu
 * @date 2018/12/20 15:13
 */
public class OkHttpUtils {

    private final static OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * get请求
     */
    public static String doGet(String url, Map<String, String> prarmsMap, Map<String, String> headersMap) throws IOException {
        if (url == null || "".equals(url.trim())) {
            return "url 不能为空";
        }
        StringBuilder urlSb = new StringBuilder(url);
        if (prarmsMap != null) {
            addParams(urlSb, prarmsMap);
        }
        Request.Builder builder = new Request.Builder();
        if (headersMap != null) {
            builder.headers(Headers.of(headersMap));
        }
        Request request = builder.url(urlSb.toString()).build();
        Response response = OK_HTTP_CLIENT.newCall(request).execute();
        if (response.body() != null) {
            return response.body().string();
        }
        return null;
    }

    public static String doGet(String url, Map<String, String> prarmsMap) throws IOException {
        return doGet(url, prarmsMap, null);
    }

    public static String doGet(String url) throws IOException {
        return doGet(url, null, null);
    }

    /**
     * post请求
     */
    public static String doPost(String url, Map<String, String> prarmsMap, Map<String, String> headersMap) throws IOException {
        if (url == null || "".equals(url.trim())) {
            return "url 不能为空";
        }
        RequestBody body = null;
        if (prarmsMap != null) {
            body = RequestBody.create(JSON, new Gson().toJson(prarmsMap));
        }
        Request.Builder builder = new Request.Builder();
        if (headersMap != null) {
            builder.headers(Headers.of(headersMap));
        }
        Request request = builder.url(url).post(body).build();
        Response response = OK_HTTP_CLIENT.newCall(request).execute();
        if (response.body() != null) {
            return response.body().string();
        }
        return null;
    }

    public static String doPost(String url, Map<String, String> prarmsMap) throws IOException {
        return doPost(url, prarmsMap, null);
    }

    public static String doPost(String url) throws IOException {
        return doPost(url, null, null);
    }

    /**
     * 为HttpGet请求拼接多个参数
     */
    public static void addParams(StringBuilder url, Map<String, String> values) {
        url.append("?");
        Set<String> keys = values.keySet();
        for (String key : keys) {
            url.append(key).append("=").append(values.get(key)).append("&");
        }
        url.deleteCharAt(url.length() - 1);
    }


    /**
     * 开启异步线程访问，访问结果自行处理
     */
    public static void enqueue(Request request, Callback responseCallback) {
        OK_HTTP_CLIENT.newCall(request).enqueue(responseCallback);
    }

    /**
     * 开启异步线程访问,不对访问结果进行处理
     */
    public static void enqueue(Request request) {
        OK_HTTP_CLIENT.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("失败处理逻辑");
            }

            @Override
            public void onResponse(Call call, Response response) {
                System.out.println("成功处理逻辑");
            }
        });
    }
}
