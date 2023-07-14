package com.example.reservation.dto.response.device;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
public class DeviceInformation {
    private String technology;
    private Map<String, List<String>> bands;
}
