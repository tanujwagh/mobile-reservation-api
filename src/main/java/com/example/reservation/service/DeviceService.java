package com.example.reservation.service;

import com.example.reservation.dto.response.DeviceResponse;
import com.example.reservation.dto.response.PageResponse;
import com.example.reservation.dto.response.ReservationResponse;
import com.example.reservation.entity.Device;
import com.example.reservation.entity.respository.DeviceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public PageResponse<DeviceResponse> getAllDevices(PageRequest pageRequest) {
        Page<DeviceResponse> page = deviceRepository
                .findAll(pageRequest)
                .map(device ->
                        DeviceResponse.builder()
                            .id(device.getId())
                            .name(device.getName())
                            .build()
                );
        return PageResponse.<DeviceResponse>builder()
                .items(page.getContent())
                .size(page.getSize())
                .page(page.getNumber())
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
