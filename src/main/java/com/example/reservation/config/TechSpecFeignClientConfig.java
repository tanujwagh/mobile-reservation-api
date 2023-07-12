package com.example.reservation.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

public class TechSpecFeignClientConfig {
    @Bean
    public RequestInterceptor requestInterceptor(@Value("${feign-clients.tech-specs.api.key}") String apiKey){
        return requestInterceptor -> requestInterceptor.header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(apiKey));
    }
}
