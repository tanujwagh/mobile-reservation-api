package com.example.reservation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String deviceId;
    private String userId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public Reservation(String deviceId, String userId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.deviceId = deviceId;
        this.userId = userId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}
