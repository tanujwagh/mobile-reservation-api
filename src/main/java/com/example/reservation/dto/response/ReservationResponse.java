package com.example.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ReservationResponse {
    private String reservationId;
    private String deviceId;
    private String userId;
    private LocalDate startDate;
    private LocalDate endDate;
}
