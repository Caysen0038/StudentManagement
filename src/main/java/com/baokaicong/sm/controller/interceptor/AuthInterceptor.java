package com.baokaicong.sm.controller.interceptor;

import com.baokaicong.sm.bean.RequestMethod;
import com.baokaicong.sm.consts.PropertyName;
import com.baokaicong.sm.global.GlobalContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 权限拦截器
 * 拦截除登录外的所有操作
 *
 * @author 包凯聪
 * @date 2020年5月12日
 *
 * 拦截器做权限检查，在细分权限操作中对路径参数识别较为复杂，弃用，使用AOP代替
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    public static String[] includePath={
            "/**"
    };

    public static String[] excludePath={

    };

    @Autowired
    private GlobalContext globalContext;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Map<String, RequestMethod> requestMap=globalContext.getParam(PropertyName.REQUEST_MAPPING);
        RequestMethod rm=requestMap.get(request.getRequestURI()+"->"+request.getMethod().toUpperCase());
        System.out.println(rm);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
}
