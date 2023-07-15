package com.example.reservation.integration;

import com.example.reservation.dto.response.ReservationResponse;
import com.example.reservation.entity.respository.DeviceRepository;
import com.example.reservation.entity.respository.ReservationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllReservation_success() throws Exception {
        mockMvc.perform(get("/reservations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(20))
                .andExpect(jsonPath("$.totalItems").value(0))
                .andExpect(jsonPath("$.totalPages").value(0));
    }

    @Test
    void getAllReservationById_success() throws Exception {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String request = """
                    {
                        "deviceId": "%s",
                        "userId": "user",
                        "startDate": "2023-07-15",
                        "endDate": "2023-07-16"
                    }
                """;

        String deviceId = deviceRepository.findAll().get(0).getId();

        String result = mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(request.formatted(deviceId, today.format(formatter), today.format(formatter)))
        ).andDo(print()).andReturn().getResponse().getContentAsString();

        ReservationResponse reservationResponse = objectMapper.readValue(result, ReservationResponse.class);

        mockMvc.perform(get("/reservations/{id}", reservationResponse.getReservationId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationId").value(reservationResponse.getReservationId()))
                .andExpect(jsonPath("$.deviceId").value(reservationResponse.getDeviceId()))
                .andExpect(jsonPath("$.userId").value(reservationResponse.getUserId()))
                .andExpect(jsonPath("$.startDate").value(reservationResponse.getStartDate().toString()))
                .andExpect(jsonPath("$.endDate").value(reservationResponse.getEndDate().toString()));
    }

    @Test
    void getAllReservationById_failure_reservationNotFound() throws Exception {
        mockMvc.perform(get("/reservations/{id}", "randomId"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Reservation not found with id - randomId"));
    }

    @Test
    void getCreateReservation_success() throws Exception {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String request = """
                    {
                        "deviceId": "%s",
                        "userId": "user",
                        "startDate": "%s",
                        "endDate": "%s"
                    }
                """;

        String deviceId = deviceRepository.findAll().get(0).getId();

        mockMvc.perform(
                post("/reservations")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(request.formatted(deviceId, today.format(formatter), today.format(formatter)))
                )
                .andDo(print())
                .andExpect(jsonPath("$.reservationId").exists())
                .andExpect(jsonPath("$.deviceId").value(deviceId))
                .andExpect(jsonPath("$.userId").value("user"))
                .andExpect(jsonPath("$.startDate").value(today.format(formatter)))
                .andExpect(jsonPath("$.endDate").value(today.format(formatter)));
    }

    @Test
    void getCreateReservation_failure_duplicateReservation() throws Exception {
        LocalDate nextDay = LocalDate.now().plus(1, ChronoUnit.DAYS);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String request = """
                    {
                        "deviceId": "%s",
                        "userId": "user",
                        "startDate": "%s",
                        "endDate": "%s"
                    }
                """;

        String deviceId = deviceRepository.findAll().get(0).getId();

        mockMvc.perform(
                        post("/reservations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request.formatted(deviceId, nextDay.format(formatter), nextDay.format(formatter)))
                );

        mockMvc.perform(
                        post("/reservations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request.formatted(deviceId, nextDay.format(formatter), nextDay.format(formatter)))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("Device already booked for %s - %s".formatted(nextDay,nextDay)));
    }
}
