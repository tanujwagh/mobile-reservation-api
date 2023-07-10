package com.example.reservation.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

public class TechSpecFeignClientConfig {
    @Bean
    public RequestInterceptor requestInterceptor(@Value("${feign-clients.tech-space.api.key}") String apiKey){
        return requestInterceptor -> requestInterceptor.request().header(HttpHeaders.AUTHORIZATION, "Bearer %".formatted(apiKey));
    }
}
