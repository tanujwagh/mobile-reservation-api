package com.example.reservation.dto.response.device;

import com.example.reservation.dto.response.ReservationResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DeviceReservation {
    private List<ReservationResponse> completedReservations;
    private List<ReservationResponse> bookedReservations;
    private Boolean isAvailable;
}
