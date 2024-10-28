package com.example.logging_example.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import com.example.logging_example.interceptor.LoggingInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final LoggingInterceptor loggingInterceptor;  // Interceptor 생성자 주입

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor).addPathPatterns("/**");  // 모든 경로에 Interceptor 적용
    }
}