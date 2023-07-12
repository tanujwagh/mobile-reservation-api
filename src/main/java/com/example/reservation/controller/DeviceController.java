package com.example.reservation.controller;

import com.example.reservation.config.TechSpecApiClient;
import com.example.reservation.dto.response.DeviceResponse;
import com.example.reservation.dto.response.PageResponse;
import com.example.reservation.service.DeviceService;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    private final TechSpecApiClient apiClient;
    private final DeviceService deviceService;

    public DeviceController(TechSpecApiClient apiClient, DeviceService deviceService) {
        this.apiClient = apiClient;
        this.deviceService = deviceService;
    }

    @GetMapping
    public PageResponse<DeviceResponse> getAllDevice(@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "20") Integer size){
        return deviceService.getAllDevices(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public Object getDeviceById(@PathVariable("id") String id){
        return apiClient.productSearch("Apple iPhone 11");
    }

    @PostMapping
    public void createDevice(@RequestBody String body){

    }

    @PutMapping("/{id}")
    public void updateDevice(@PathVariable("id") String id){

    }

    @DeleteMapping("/{id}")
    public void deleteDevice(@PathVariable("id") String id){

    }
}
