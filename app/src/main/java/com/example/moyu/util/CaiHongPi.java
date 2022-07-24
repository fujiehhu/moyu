package com.example.moyu.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class CaiHongPi {
    static String httpUrl = "https://api.tianapi.com/txapi/caihongpi/index";
    static String httpArg = "key=e5d7f0a9cb6bbf8981e0b783ab43cbce";

    public static String getPi() throws ExecutionException, InterruptedException, TimeoutException {
        // 两个线程的线程池
        ExecutorService executor = Executors.newFixedThreadPool(2);


        //jdk1.8之前的实现方式
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            //模拟耗时操作
            return request(httpUrl, httpArg);
        }, executor);
        //采用lambada的实现方式
        return future.get(5000, TimeUnit.MILLISECONDS);
    }

    public static String request(String httpUrl, String httpArg) {
        String result = null;
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL uri = new URL(httpUrl); // 创建URL对象
            HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
            conn.setConnectTimeout(5000); // 设置相应超时
            conn.setRequestMethod("GET");

            int statusCode = conn.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                System.out.println("Http错误码：" + statusCode);
            }

            // 读取服务器的数据
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }

            result = builder.toString();
            br.close();
            is.close();
            conn.disconnect(); // 断开连接
        } catch (Exception e) {
            e.printStackTrace();
        }
        return process(result);
    }

    public static String process(String jsonResult) {
        if (jsonResult == null) return "";
        Map map = JSON.parseObject(jsonResult, Map.class);
        JSONArray objects = JSONArray.parseArray(map.get("newslist") + "");
        Map map1 = JSON.parseObject(objects.get(0).toString(), Map.class);
        return map1.get("content").toString();
    }
}
