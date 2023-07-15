package com.example.reservation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    private LocalDate startDate;
    private LocalDate endDate;

    public Reservation(String deviceId, String userId, LocalDate startDate, LocalDate endDate) {
        this.deviceId = deviceId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
