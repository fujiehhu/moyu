package com.example.moyu.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class WeiBoApi extends BaseApi {


    public ArrayList<String> getWeiBo() throws ExecutionException, InterruptedException, TimeoutException {
        httpUrl = "http://api.tianapi.com/txapi/weibohot/index";
        httpArg = "key=e5d7f0a9cb6bbf8981e0b783ab43cbce";
        return process(future().get(5000, TimeUnit.MILLISECONDS));
    }

    public ArrayList<String> process(String jsonResult) {
        ArrayList<String> res = new ArrayList<>();
        Map map = JSON.parseObject(jsonResult, Map.class);
        JSONArray objects = JSONArray.parseArray(map.get("newslist") + "");
        for (int j = 0; j < objects.size(); j++) {
            Map map1 = JSON.parseObject(objects.get(j).toString(), Map.class);
            res.add(map1.get("hotword") + "\uD83D\uDD25" + map1.get("hotwordnum"));
        }
        return res;
    }
}
