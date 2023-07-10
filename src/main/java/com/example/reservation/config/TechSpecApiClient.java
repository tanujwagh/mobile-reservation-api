package com.example.reservation.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "https://api.techspecs.io/v4", configuration = TechSpecFeignClientConfig.class)
public interface TechSpecApiClient {
    @PostMapping("/product/search")
    void productSearch(@RequestParam("query") String key);

}
