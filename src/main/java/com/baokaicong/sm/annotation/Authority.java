package com.baokaicong.sm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 权限注解
 * 用于标记方法执行权限
 *
 * @author 包凯聪
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Authority {
    String value() default "0";
    boolean open() default false;
}
