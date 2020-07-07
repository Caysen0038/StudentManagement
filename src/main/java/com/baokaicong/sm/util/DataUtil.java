package com.baokaicong.sm.util;

public class DataUtil {
    public DataUtil() {
    }

    public static String byte2Hex(byte[] data) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;

        for(int i = 0; i < data.length; ++i) {
            temp = Integer.toHexString(data[i] & 255);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }

            stringBuffer.append(temp);
        }

        return stringBuffer.toString();
    }

    public static int byte2Int(byte[] data) {
        return (data[0] & -16777216) + (data[1] & 16711680) + (data[2] & '\uff00') + (data[3] & 255);
    }

    public static byte[] int2Byte(int n) {
        byte[] data = new byte[]{(byte)(n >> 24 & 255), (byte)(n >> 16 & 255), (byte)(n >> 8 & 255), (byte)(n >> 0 & 255)};
        return data;
    }
}
