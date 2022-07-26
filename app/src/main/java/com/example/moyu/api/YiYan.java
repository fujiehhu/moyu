package com.example.moyu.api;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class YiYan extends BaseApi {

    public String getYiYan() throws ExecutionException, InterruptedException, TimeoutException {
        httpUrl = "http://api.guaqb.cn/v1/onesaid/";

        return future().get(5000, TimeUnit.MILLISECONDS);

    }
}
