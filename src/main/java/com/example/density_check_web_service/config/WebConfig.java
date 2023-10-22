package com.example.density_check_web_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final String fileDir = System.getProperty("user.dir") + "/src/main/resources/static/img/profile/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println(fileDir);
        registry.addResourceHandler("/img/profile/**").addResourceLocations("file:/"+fileDir);
    }
}