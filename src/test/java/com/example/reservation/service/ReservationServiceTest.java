package com.example.reservation.service;

import com.example.reservation.dto.request.ReservationRequest;
import com.example.reservation.dto.response.PageResponse;
import com.example.reservation.dto.response.ReservationResponse;
import com.example.reservation.entity.respository.DeviceRepository;
import com.example.reservation.entity.respository.ReservationRepository;
import com.example.reservation.exception.NotFoundException;
import com.example.reservation.exception.ReservationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private ReservationService reservationService;


    @Test
    void testGetAllReservation_success(){
        PageRequest pageRequest = PageRequest.of(1, 2);
        PageResponse<ReservationResponse> allReservations = reservationService.getAllReservations(pageRequest);

        assertThat(allReservations)
                .isNotNull()
                .extracting(PageResponse::getSize).isEqualTo(2);

        assertThat(allReservations.getItems())
                .hasSize(0);
    }

    @Test
    void testCreateReservation_success() throws ReservationException, NotFoundException {
        String deviceId = deviceRepository.findAll().get(0).getId();

        ReservationRequest request = new ReservationRequest();
        request.setDeviceId(deviceId);
        request.setUserId("userId");
        request.setStartDate(LocalDate.now());
        request.setEndDate(LocalDate.now());

        ReservationResponse reservation = reservationService.createReservation(request);

        assertThat(reservation)
                .isNotNull()
                .extracting(ReservationResponse::getReservationId).isNotNull();
    }

    @Test
    void testCreateReservation_failure_deviceNotFound(){
        ReservationRequest request = new ReservationRequest();
        request.setDeviceId("randomId");
        request.setUserId("userId");
        request.setStartDate(LocalDate.now());
        request.setEndDate(LocalDate.now());

        assertThatThrownBy(() -> reservationService.createReservation(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Device not found with id - randomId");
    }

    @Test
    void testCreateReservation_failure_alreadyReserved() throws ReservationException, NotFoundException {
        reservationRepository.deleteAll();
        String deviceId = deviceRepository.findAll().get(0).getId();

        ReservationRequest request = new ReservationRequest();
        request.setDeviceId(deviceId);
        request.setUserId("userId");
        request.setStartDate(LocalDate.now());
        request.setEndDate(LocalDate.now());

        ReservationResponse reservationResponse = reservationService.createReservation(request);

        assertThatThrownBy(() -> reservationService.createReservation(request))
                .isInstanceOf(ReservationException.class)
                .hasMessage("Device already booked for %s - %s".formatted(request.getStartDate(), request.getEndDate()));
    }

}
