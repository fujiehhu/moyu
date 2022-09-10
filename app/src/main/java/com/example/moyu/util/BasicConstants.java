package com.example.moyu.util;

public enum BasicConstants {

    ZHOUMO("周末", "2022-09-10 00:00:00"),
    ZHONGQIU("中秋节", "2023-09-29 00:00:00"),
    GUOQING("国庆节", "2022-10-01 00:00:00"),
    YUANDAN("元旦", "2023-01-01 00:00:00"),
    CHUNJIE("春节", "2023-02-11 00:00:00"),
    QINGMING("清明", "2023-04-03 00:00:00"),
    LAODONGJIE("劳动节", "2023-05-01 00:00:00"),


    AM("AM", "早上好"),
    NOON("NOON", "中午好"),
    PM("PM", "晚上好"),
    OTHER("OTHER", "你好");


    private String name;
    private String value;

    BasicConstants(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
