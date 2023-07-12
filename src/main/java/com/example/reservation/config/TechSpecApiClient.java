package com.example.reservation.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tech-specs-api-client", url = "${feign-clients.tech-specs.api.base-url}", configuration = TechSpecFeignClientConfig.class)
public interface TechSpecApiClient {
    @PostMapping("/product/search")
    Object productSearch(@RequestParam("query") String key);

}
