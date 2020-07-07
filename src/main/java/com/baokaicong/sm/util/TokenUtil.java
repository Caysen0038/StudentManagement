package com.baokaicong.sm.util;

import com.baokaicong.sm.bean.UserToken;

public class TokenUtil {

    public static boolean parseUserToken(UserToken userToken){
        String[] value;
        try{
            String token= MessageUtil.DBEA(userToken.getToken(),userToken.getKey());
            if(token.contains("&") && (value=token.split("&")).length==2){
                userToken.setUid(value[0]);
                userToken.setInfo(value[1]);
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }

    }


    public static boolean buildUserToken(UserToken userToken){
        try{
            userToken.setToken(MessageUtil.BEA(
                    userToken.getUid()+
                            "&"+
                            userToken.getInfo(),
                    userToken.getKey()));
            return true;
        }catch (Exception e){
            return false;
        }

    }

}
