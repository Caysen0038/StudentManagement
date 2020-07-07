package com.baokaicong.sm.controller.interceptor;

import com.baokaicong.sm.exception.SMException;
import com.baokaicong.sm.bean.UserToken;
import com.baokaicong.sm.consts.PropertyName;
import com.baokaicong.sm.global.GlobalContext;
import com.baokaicong.sm.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户Token拦截器
 * 拦截除登录外的所有操作
 */
@Component
public class UserTokenInterceptor implements HandlerInterceptor {

    public static String[] includePath={
            "/**"
    };

    public static String[] excludePath={

    };

    @Autowired
    private GlobalContext globalContext;

    private static final String LOGIN_URI="/users/";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri=request.getRequestURI();
        String method=request.getMethod();
        if(isLoginURI(uri,method)){
            return true;
        }
        String token=request.getHeader("user-token");
        if(token!=null){
            UserToken userToken=UserToken.builder()
                            .key(globalContext.getParam(PropertyName.TOKEN_KEY).toString())
                            .token(token)
                            .build();
            if(TokenUtil.parseUserToken(userToken)){
                if(request.getRemoteAddr().equals(userToken.getInfo())){
                    // 未做时间验证，后续补充
                    request.setAttribute("uid",userToken.getUid());
                    return true;
                }
            }
        }
        System.err.println("Token验证不通过");
        throw new SMException("用户Token无效","401");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    private boolean isLoginURI(String uri,String method){
        return uri.startsWith(LOGIN_URI) && uri.length()>LOGIN_URI.length() && method.equals("POST");
    }

}
