package com.example.reservation.integration;

import com.example.reservation.entity.respository.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DeviceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DeviceRepository deviceRepository;

    @Test
    void testGetAllDevice_success() throws Exception {
        mockMvc.perform(get("/devices"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(20))
                .andExpect(jsonPath("$.totalItems").value(deviceRepository.findAll().size()))
                .andExpect(jsonPath("$.totalPages").value(1));
    }

    @Test
    void testGetDeviceById_success() throws Exception {
        String deviceId = deviceRepository.findAll().get(0).getId();
        mockMvc.perform(get("/devices/{id}", deviceId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(deviceId))
                .andExpect(jsonPath("$.reservations.isAvailable").value(true));
    }

    @Test
    void testGetDeviceById_failure_deviceNotFound() throws Exception {
        mockMvc.perform(get("/devices/{id}", "randomId"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Device not found with id - randomId"));
    }

}
