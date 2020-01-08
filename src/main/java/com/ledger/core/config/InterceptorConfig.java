package com.ledger.core.config;

import com.ledger.core.interceptor.TokenInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author pang
 * @version V1.0
 * @ClassName: InterceptorConfig
 * @Package com.pang.mall.config
 * @description: 拦截器配置
 * @date 2019/11/11 22:42
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(InterceptorConfig.class);
    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LOGGER.debug("拦截器初始化开始");
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/api/**");
        LOGGER.debug("拦截器初始化完毕");
    }
}
