package com.baokaicong.sm.util;

import java.util.Random;

public class StringUtil {

    public static String getRandomNumber(int len){
        StringBuilder sb=new StringBuilder();
        Random random=new Random();
        while(len-->0){
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

    public static boolean isEmpty(String str){
        return str==null || str.length()==0;
    }

}
