package com.example.moyu.api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BaseApi {

    // 两个线程的线程池
    ExecutorService executor = Executors.newFixedThreadPool(2);

    String httpUrl;

    String httpArg;

    //jdk1.8之前的实现方式
    public CompletableFuture<String> future() {
        return CompletableFuture.supplyAsync(() -> {
            //模拟耗时操作
            //json 的处理在各个子类中，这里只是获取json
            if (httpArg != null) {
                return request(httpUrl, httpArg);
            }
            return request(httpUrl);
        }, executor);
    }


    public String request(String httpUrl) {
        String result = null;

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
        return result;

    }

    public String request(String httpUrl, String httpArg) {
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
        return result;

    }

    //json 的处理在各个子类中，这里只是获取json
//    public ArrayList<String> process(String jsonResult) {
//
//        return "";
//    }
}
