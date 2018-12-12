package com.lz.palmscore.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by 白 on 2018/12/12.
 */

/**
 *  各个拦截器配置
 */
@Configuration
public class WebConfigurer  implements WebMvcConfigurer {

    @Autowired
    AdminInterceptor addInterceptor;

    /**
     *  开发中 注释拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截所有 前缀 admin 的请求，除了 login 和 register
        //registry.addInterceptor(addInterceptor).addPathPatterns("/admin/**").excludePathPatterns("/admin/login", "/admin/register");
    }
}
