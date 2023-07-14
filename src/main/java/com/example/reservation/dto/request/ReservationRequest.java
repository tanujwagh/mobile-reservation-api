package com.example.reservation.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReservationRequest {
    private String deviceId;
    private String userId;
    private LocalDate startDate;
    private LocalDate endDate;
}
