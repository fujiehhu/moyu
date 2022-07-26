package com.example.moyu.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class BaseApi {
    String httpUrl;
    String httpArg;

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
        return result;

    }


    public static String process(String jsonResult) {

        return "";
    }
}
