package com.example.reservation.dto.request;

import com.example.reservation.validation.ReservationDate;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReservationRequest {
    @NotEmpty(message = "Device Id is required")
    private String deviceId;
    @NotEmpty(message = "User Id is required")
    private String userId;
    @ReservationDate(message = "Start date must not be empty or before current date")
    private LocalDate startDate;
    @ReservationDate(message = "End date must be not be empty or before current date")
    private LocalDate endDate;
}
