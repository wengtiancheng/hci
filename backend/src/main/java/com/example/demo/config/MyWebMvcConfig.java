package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: DingXiaoyu
 * @Date: 0:17 2023/11/26
 *
 * 这个类实现了WebMvcConfigurer接口，
 * 表示会被SpringBoot接受，
 * 这个类的作用是配置拦截器。
 * addInterceptors方法配置了拦截器，
 * 添加了loginInterceptor作为拦截器，
 * 并且设置除了register和login的所有接口都需要通过该拦截器。
*/
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
    @Autowired
    LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/users/register")
                .excludePathPatterns("/api/users/login")
                .excludePathPatterns("/api/stores/all")
                .excludePathPatterns("/api/ali/*")
                //非登录状态下获取全部 装机方案,开放相关接口的权限
                .excludePathPatterns("/api/solution/all")
                .excludePathPatterns("/api/cpu/all")
                .excludePathPatterns("/api/gpu/all")
                .excludePathPatterns("/api/memory/all")
                .excludePathPatterns("/api/harddisk/all")
                .excludePathPatterns("/api/cooling/all")
                .excludePathPatterns("/api/chassis/all")
                .excludePathPatterns("/api/display/all")
                .excludePathPatterns("/api/powersupply/all")
                .excludePathPatterns("/api/motherboard/all")
                .order(1);
    }

}
