package com.example.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeviceResponse {
    private String id;
    private String name;
}
