package com.ming.cnbeta.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ming on 16/2/25.
 */
public class TimeUtils {

    public static String getBeforeTime(long beforeTime){
        long current = System.currentTimeMillis();
        long passTime = current - beforeTime;
        if (passTime > 0){
            if (passTime == 0 || passTime < 1000){
                return "刚刚";
            }

            if (passTime < 60 * 1000){
                return passTime / 1000 + "秒以前";
            }

            if (passTime < 60 * 60 * 1000 ){
                return passTime / 1000 /60 + "分钟以前";
            }

            if (passTime < 60 * 60 * 60 * 1000 ){
                return passTime / 1000 /60 /60 + "小时以前";
            }

            if (passTime < 24 * 60 * 60 * 60 * 1000 ){
                return passTime / 1000 /60 /60 /24 + "天以前";
            }

        }
        return "";
    }

    /**
     * 将字符串型日期转换为日期型
     * @param strDate
     * @param srcDateFormat
     * @return
     */
    public static Date stringToDate(String strDate, String srcDateFormat) {
        Date tmpDate = (new SimpleDateFormat(srcDateFormat)).parse(strDate, new ParsePosition(0));
        return tmpDate;
    }
}
