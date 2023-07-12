package com.example.reservation.service;


import com.example.reservation.dto.response.PageResponse;
import com.example.reservation.dto.request.ReservationRequest;
import com.example.reservation.dto.response.ReservationResponse;
import com.example.reservation.entity.Reservation;
import com.example.reservation.entity.respository.DeviceRepository;
import com.example.reservation.entity.respository.ReservationRepository;
import com.example.reservation.exception.ReservationException;
import com.example.reservation.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final DeviceRepository deviceRepository;

    public ReservationService(ReservationRepository reservationRepository, DeviceRepository deviceRepository) {
        this.reservationRepository = reservationRepository;
        this.deviceRepository = deviceRepository;
    }

    @Transactional
    public ReservationResponse createReservation(ReservationRequest request) throws ReservationException {
        deviceRepository.findById(request.getDeviceId()).orElseThrow(() -> new NotFoundException("Device", request.getDeviceId()));
        boolean reserved = reservationRepository
                .findAllByDeviceId(request.getDeviceId())
                .stream()
                .anyMatch(reservation -> checkReserved(reservation, request));

        if(reserved){
            throw new ReservationException("Device already booked for %s - %s".formatted(request.getStartDateTime(), request.getEndDateTime()));
        }

        Reservation reservation = reservationRepository.save(
            new Reservation(
                request.getDeviceId(),
                request.getUserId(),
                request.getStartDateTime(),
                request.getEndDateTime()
            )
        );

        return ReservationResponse.builder()
                .reservationId(reservation.getId())
                .deviceId(reservation.getDeviceId())
                .userId(reservation.getUserId())
                .startDateTime(reservation.getStartDateTime())
                .endDateTime(reservation.getEndDateTime())
                .build();
    }

    private boolean checkReserved(Reservation reservation, ReservationRequest request) {
        return (reservation.getStartDateTime().equals(request.getStartDateTime()) && reservation.getEndDateTime().equals(request.getEndDateTime())) ||
                (reservation.getStartDateTime().isAfter(request.getStartDateTime()) && reservation.getStartDateTime().isBefore(request.getEndDateTime())) ||
                (reservation.getEndDateTime().isAfter(request.getStartDateTime()) && reservation.getEndDateTime().isBefore(request.getEndDateTime()));
    }

    @Transactional
    public void deleteReservation(String id) throws NotFoundException {
        Reservation reservation = reservationRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation", id));

        reservationRepository.delete(reservation);
    }

    public ReservationResponse getReservation(String id) throws NotFoundException {
        return reservationRepository
                .findById(id)
                .map(reservation ->
                        ReservationResponse.builder()
                            .reservationId(reservation.getId())
                            .deviceId(reservation.getDeviceId())
                            .userId(reservation.getUserId())
                            .startDateTime(reservation.getStartDateTime())
                            .endDateTime(reservation.getEndDateTime())
                            .build()
                )
                .orElseThrow(() -> new NotFoundException("Reservation", id));
    }

    public PageResponse<ReservationResponse> getAllReservations(PageRequest pageRequest) {
        Page<ReservationResponse> page = reservationRepository
                .findAll(pageRequest)
                .map(reservation ->
                        ReservationResponse.builder()
                                .reservationId(reservation.getId())
                                .deviceId(reservation.getDeviceId())
                                .userId(reservation.getUserId())
                                .startDateTime(reservation.getStartDateTime())
                                .endDateTime(reservation.getEndDateTime())
                                .build()
                );

        return PageResponse.<ReservationResponse>builder()
                .items(page.getContent())
                .size(page.getSize())
                .page(page.getNumber())
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();

    }
}
