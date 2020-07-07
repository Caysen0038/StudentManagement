package com.baokaicong.sm.controller.config;

import com.baokaicong.sm.bean.entity.Auth;
import com.baokaicong.sm.controller.interceptor.AuthInterceptor;
import com.baokaicong.sm.controller.interceptor.UserTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.FormContentFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 配置MVC
 */
@Configuration
public class MvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    private UserTokenInterceptor userTokenInterceptor;

    @Autowired
    private AuthInterceptor authInterceptor;

    /**
     * 配置拦截器
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(userTokenInterceptor)
                .addPathPatterns(UserTokenInterceptor.includePath)
                .excludePathPatterns(UserTokenInterceptor.excludePath);

        /**
         * 拦截器做权限拦截做细分权限较为复杂，弃用，改用AOP
         */
//        registry.addInterceptor(authInterceptor)
//                .addPathPatterns(AuthInterceptor.includePath)
//                .excludePathPatterns(AuthInterceptor.excludePath);
        super.addInterceptors(registry);
    }

//    @Bean
//    public HttpPutFormContentFilter httpPutFormContentFilter() {
//        return new HttpPutFormContentFilter();
//    }

    @Bean
    public FormContentFilter httpPutFormContentFilter() {
        return new FormContentFilter();
    }
}
