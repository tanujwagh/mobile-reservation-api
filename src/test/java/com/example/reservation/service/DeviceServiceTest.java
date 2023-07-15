package com.example.reservation.service;

import com.example.reservation.dto.response.PageResponse;
import com.example.reservation.dto.response.device.DeviceDetailResponse;
import com.example.reservation.dto.response.device.DeviceReservation;
import com.example.reservation.dto.response.device.DeviceResponse;
import com.example.reservation.entity.Device;
import com.example.reservation.entity.respository.DeviceRepository;
import com.example.reservation.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
class DeviceServiceTest {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceService deviceService;

    @Test
    void testGetAllDevice_success() {
        PageRequest pageRequest = PageRequest.of(1, 2);
        PageResponse<DeviceResponse> allDevices = deviceService.getAllDevices(pageRequest);

        assertThat(allDevices)
                .isNotNull()
                .extracting(PageResponse::getSize).isEqualTo(2);

        assertThat(allDevices.getItems())
                .hasSize(2);

        assertThat(allDevices.getTotalItems()).isEqualTo(deviceRepository.findAll().size());

    }


    @Test
    void testGetDeviceById_success() throws NotFoundException {
        Device device = deviceRepository.findAll().get(0);

        DeviceDetailResponse deviceDetailResponse = deviceService.getDeviceById(device.getId());

        assertThat(deviceDetailResponse)
                .isNotNull()
                .isInstanceOf(DeviceDetailResponse.class)
                .extracting(DeviceDetailResponse::getId).isEqualTo(device.getId());

        assertThat(deviceDetailResponse.getReservations())
                .isNotNull()
                .extracting(DeviceReservation::getIsAvailable).isEqualTo(Boolean.TRUE);
    }

    @Test
    void testGetDeviceById_failure_deviceNotFound() throws NotFoundException {
        assertThatThrownBy(() -> deviceService.getDeviceById("randomId"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Device not found with id - randomId");
    }
}
