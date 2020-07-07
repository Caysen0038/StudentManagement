package com.baokaicong.sm.global.initializer;

import com.baokaicong.sm.annotation.Authority;
import com.baokaicong.sm.bean.RequestMethod;
import com.baokaicong.sm.consts.PropertyName;
import com.baokaicong.sm.global.GlobalContext;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.web.servlet.ControllerEndpointHandlerMapping;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

@Component
public class GlobalInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private GlobalContext globalContext;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //globalContext.putParam(PropertyName.TOKEN_KEY,System.currentTimeMillis()+"");
        //loadRequestMapping();
        globalContext.putParam(PropertyName.TOKEN_KEY,"15723245423");
    }

    private void loadRequestMapping(){
        Map<String,RequestMethod> requestMap=new HashMap<>();
        //获取所有的RequestMapping
        Map<String, HandlerMapping> allRequestMappings = BeanFactoryUtils.beansOfTypeIncludingAncestors(webApplicationContext,
                HandlerMapping.class, true, false);
        for (HandlerMapping handlerMapping : allRequestMappings.values()) {
            //本项目只需要RequestMappingHandlerMapping中的URL映射
            if (handlerMapping instanceof RequestMappingHandlerMapping) {
                RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) handlerMapping;
                Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
                for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : handlerMethods.entrySet()) {
                    RequestMappingInfo requestMappingInfo = requestMappingInfoHandlerMethodEntry.getKey();
                    HandlerMethod mappingInfoValue = requestMappingInfoHandlerMethodEntry.getValue();
                    RequestMethod rm=new RequestMethod()
                            .setPath(requestMappingInfo.getPatternsCondition().toString().replace("[","").replace("]",""))
                            .setType(requestMappingInfo.getMethodsCondition().toString().replace("[","").replace("]",""))
                            .setController(webApplicationContext.getBean(mappingInfoValue.getBean().toString()))
                            .setMethod(mappingInfoValue.getMethod());
                    Authority auth=rm.getMethod().getAnnotation(Authority.class);
                    if(auth!=null){
                        rm.setAuth(auth.value());
                    }
                    requestMap.put(rm.getPath()+"->"+rm.getType(),rm);
                }
            }
        }
        globalContext.putParam(PropertyName.REQUEST_MAPPING,requestMap);
    }
}
