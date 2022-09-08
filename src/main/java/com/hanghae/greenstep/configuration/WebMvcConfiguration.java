package com.hanghae.greenstep.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000","http://localhost:8080","http://13.209.16.253:8080" ,"https://greenstepapp.com","https://greenstepserver.link")
                .allowedMethods("*")
                .exposedHeaders("Authorization","Refresh_Token","Access_Token_Expire_Time")
                .allowCredentials(true)//make client read header("jwt-token")
        ;
    }
}
