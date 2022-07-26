package com.example.moyu.api;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.moyu.api.BaseApi;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CaiHongPi extends BaseApi {

    public String getPi() throws ExecutionException, InterruptedException, TimeoutException {
        httpUrl = "https://api.tianapi.com/txapi/caihongpi/index";
        httpArg = "key=e5d7f0a9cb6bbf8981e0b783ab43cbce";

        // 两个线程的线程池
        ExecutorService executor = Executors.newFixedThreadPool(2);

        //jdk1.8之前的实现方式
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            //模拟耗时操作
            String result = request(httpUrl, httpArg);
            return process(result);
        }, executor);
        //采用lambada的实现方式
        return future.get(5000, TimeUnit.MILLISECONDS);
    }

    public static String process(String jsonResult) {
        if (jsonResult == null) return "";
        Map map = JSON.parseObject(jsonResult, Map.class);
        JSONArray objects = JSONArray.parseArray(map.get("newslist") + "");
        Map map1 = JSON.parseObject(objects.get(0).toString(), Map.class);
        return map1.get("content").toString();
    }
}
