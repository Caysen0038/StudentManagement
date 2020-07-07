package com.baokaicong.sm.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    private static final SimpleDateFormat format=new SimpleDateFormat();

    private DateUtil(){}

    public static String getDateTime(){
        return get("yyyy-MM-dd HH:mm:ss");
    }

    public static String get(String pattern){
        format.applyPattern(pattern);
        return format.format(new Date());
    }

}
