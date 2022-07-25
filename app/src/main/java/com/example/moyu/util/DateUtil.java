package com.example.moyu.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {


    public static String getDateNow() throws ParseException {
        Calendar now = Calendar.getInstance();
        System.out.println("年: " + now.get(Calendar.YEAR));
        System.out.println("月: " + (now.get(Calendar.MONTH) + 1) + "");
        System.out.println("日: " + now.get(Calendar.DAY_OF_MONTH));
        System.out.println("时: " + now.get(Calendar.HOUR_OF_DAY));
        System.out.println("分: " + now.get(Calendar.MINUTE));
        System.out.println("秒: " + now.get(Calendar.SECOND));
        System.out.println("当前时间毫秒数：" + now.getTimeInMillis());
        System.out.println(now.getTime());

        Date d = new Date();
        System.out.println(d);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        System.out.println("格式化后的日期：" + dateNowStr);

        String str = "2022-1-13 17:26:33"; //要跟上面sdf定义的格式一样
        Date today = sdf.parse(str);
        System.out.println("字符串转成日期：" + today);

//        return sdf.format(d);

        return now.get(Calendar.YEAR) + "年"
                + (now.get(Calendar.MONTH) + 1) + "月"
                + now.get(Calendar.DAY_OF_MONTH) + "日";
    }

    /**
     * 根据小时判断是否为上午、中午、下午
     *
     * @return
     * @author zhangsq
     */

    public static String getDuringDay() {
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);

        if (hour >= 7 && hour < 11) {

            return BasicConstants.AM.getValue();

        }
        if (hour >= 11 && hour < 13) {

            return BasicConstants.NOON.getValue();

        }
        if (hour >= 13 && hour <= 18) {

            return BasicConstants.PM.getValue();

        } else {

            return BasicConstants.OTHER.getValue();
        }


    }

    /**
     * 根据日期获得星期的方法
     *
     * @return
     */

    public static String getWeekOfDate() {
        String[] weekDaysName = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());

        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        return weekDaysName[intWeek];

    }

    public static Integer getWeekOfDateIndex() {

        Integer[] weekDaysName = {7, 1, 2, 3, 4, 5, 6};

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());

        int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        return weekDaysName[intWeek];

    }


    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 　　 *字符串的日期格式的计算
     */
    public static int daysBetween(String bdate) throws ParseException {
        Date d = new Date();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String smdate = sdf.format(d);

        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    public static String countDown(String type) throws ParseException {
        //具体的天数
        int days = 0;
        if (BasicConstants.ZHOUMO.getName().equals(type)) {
            if (getWeekOfDateIndex().equals(5)) {
                days = -1;
            } else {
                days = 6 - getWeekOfDateIndex();
            }
        }
        if (BasicConstants.ZHONGQIU.getName().equals(type)) {
            days = daysBetween(BasicConstants.ZHONGQIU.getValue());
        }
        if (BasicConstants.GUOQING.getName().equals(type)) {
            days = daysBetween(BasicConstants.GUOQING.getValue());
        }
        if (BasicConstants.YUANDAN.getName().equals(type)) {
            days = daysBetween(BasicConstants.YUANDAN.getValue());
        }
        if (BasicConstants.CHUNJIE.getName().equals(type)) {
            days = daysBetween(BasicConstants.CHUNJIE.getValue());
        }
        if (BasicConstants.QINGMING.getName().equals(type)) {
            days = daysBetween(BasicConstants.QINGMING.getValue());
        }
        if (BasicConstants.LAODONGJIE.getName().equals(type)) {
            days = daysBetween(BasicConstants.LAODONGJIE.getValue());
        }

        return String.valueOf(days);
    }
}

