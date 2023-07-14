package com.example.reservation.entity.respository;

import com.example.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, String> {
    List<Reservation> findAllByDeviceId(String deviceId);
}
