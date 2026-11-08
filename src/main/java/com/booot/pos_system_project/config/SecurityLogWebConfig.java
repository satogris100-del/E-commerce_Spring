package com.booot.pos_system_project.config;

import com.booot.pos_system_project.security.SecurityLogInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class SecurityLogWebConfig implements WebMvcConfigurer{
    private final SecurityLogInterceptor securityLogInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry  registry) {
        registry.addInterceptor(securityLogInterceptor).addPathPatterns("api/**");
    }
}