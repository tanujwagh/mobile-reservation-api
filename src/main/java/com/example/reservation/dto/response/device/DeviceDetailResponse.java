package com.example.reservation.dto.response.device;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceDetailResponse extends DeviceResponse {

    private DeviceReservation reservations;
    private DeviceInformation information;

    public DeviceDetailResponse(String id, String name) {
        super(id, name);
    }
}
