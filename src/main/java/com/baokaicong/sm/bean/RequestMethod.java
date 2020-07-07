package com.baokaicong.sm.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.lang.reflect.Method;

@Data
@Accessors(chain = true)
public class RequestMethod {
    private String path;
    private String type;
    private Object controller;
    private Method method;
    private String auth;
}
