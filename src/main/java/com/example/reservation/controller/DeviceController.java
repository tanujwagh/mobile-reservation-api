package com.example.reservation.controller;

import com.example.reservation.config.TechSpecApiClient;
import com.example.reservation.dto.response.PageResponse;
import com.example.reservation.dto.response.device.DeviceDetailResponse;
import com.example.reservation.dto.response.device.DeviceResponse;
import com.example.reservation.exception.ApiException;
import com.example.reservation.exception.NotFoundException;
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
    public PageResponse<DeviceResponse> getAllDevice(@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "20") Integer size) {
        return deviceService.getAllDevices(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public DeviceDetailResponse getDeviceById(@PathVariable("id") String id) throws NotFoundException {
        return deviceService.getDeviceById(id);
    }

    @PostMapping
    public void createDevice(@RequestBody String body) throws ApiException {
        throw new ApiException("Not supported");
    }

    @PutMapping("/{id}")
    public void updateDevice(@PathVariable("id") String id) throws ApiException {
        throw new ApiException("Not supported");
    }

    @DeleteMapping("/{id}")
    public void deleteDevice(@PathVariable("id") String id) throws ApiException {
        throw new ApiException("Not supported");
    }
}
