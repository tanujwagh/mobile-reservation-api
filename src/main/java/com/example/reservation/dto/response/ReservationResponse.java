package com.example.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReservationResponse {
    private String reservationId;
    private String deviceId;
    private String userId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
