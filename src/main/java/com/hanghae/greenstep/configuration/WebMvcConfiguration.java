package com.hanghae.greenstep.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000","http://localhost:8080", "http://54.180.30.74", "http://54.180.30.74:8080")
                .allowedMethods("*")
                .exposedHeaders("Authorization","Refresh-Token")
                .allowCredentials(true)//make client read header("jwt-token")
        ;
    }
}
