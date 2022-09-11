package com.example.moyu.api;

import com.alibaba.fastjson.JSON;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JinRiShiCiApi extends BaseApi {


    public String getShiCi() throws ExecutionException, InterruptedException, TimeoutException {
        httpUrl = "https://tenapi.cn/comment/";
        return process(future().get(5000, TimeUnit.MILLISECONDS));

    }

    public String process(String jsonResult) {
        if (jsonResult == null) {
            System.out.println("json is null");
            return "";
        }
        Map map = JSON.parseObject(jsonResult, Map.class);
        Map map1 = JSON.parseObject(map.get("data").toString(), Map.class);
        return map1.get("content").toString();
    }

}
