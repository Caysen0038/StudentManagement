package com.baokaicong.sm.global;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局上下文，存储必要的信息及配置
 */
@Component
public class GlobalContext implements InitializingBean {

    private Map<String,Object> param;

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }

    public void init(){
        param=new HashMap<>();
    }

    public <T> T getParam(String name){
        return (T)param.get(name);
    }

    public void putParam(String name,Object value){
        param.put(name,value);
    }


}
