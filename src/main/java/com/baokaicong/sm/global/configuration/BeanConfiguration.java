package com.baokaicong.sm.global.configuration;

import com.github.pagehelper.PageHelper;
import org.aidework.core.object.BeanDuplicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HttpPutFormContentFilter;

import java.util.Properties;

@Configuration
public class BeanConfiguration {


    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum","true");
        properties.setProperty("rowBoundsWithCount","true");
        properties.setProperty("reasonable","true");
        properties.setProperty("dialect","mysql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }


    @Bean
    public BeanDuplicator buildBeanDuplicator(){
        return new BeanDuplicator();
    }
}
