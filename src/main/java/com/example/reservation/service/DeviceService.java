package com.example.reservation.service;

import com.example.reservation.dto.response.PageResponse;
import com.example.reservation.dto.response.ReservationResponse;
import com.example.reservation.dto.response.device.DeviceDetailResponse;
import com.example.reservation.dto.response.device.DeviceInformation;
import com.example.reservation.dto.response.device.DeviceReservation;
import com.example.reservation.dto.response.device.DeviceResponse;
import com.example.reservation.entity.Device;
import com.example.reservation.entity.Reservation;
import com.example.reservation.entity.respository.DeviceRepository;
import com.example.reservation.entity.respository.ReservationRepository;
import com.example.reservation.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final ReservationRepository reservationRepository;

    public DeviceService(DeviceRepository deviceRepository, ReservationRepository reservationRepository) {
        this.deviceRepository = deviceRepository;
        this.reservationRepository = reservationRepository;
    }

    public PageResponse<DeviceResponse> getAllDevices(PageRequest pageRequest) {
        Page<DeviceResponse> page = deviceRepository
                .findAll(pageRequest)
                .map(device -> new DeviceResponse(device.getId(), device.getName()));
        return PageResponse.<DeviceResponse>builder()
                .items(page.getContent())
                .size(page.getSize())
                .page(page.getNumber())
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    public DeviceDetailResponse getDeviceById(String id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Device", id));

        List<ReservationResponse> completedReservations = new ArrayList<>();
        List<ReservationResponse> bookedReservations = new ArrayList<>();
        Boolean available = true;
        LocalDate today = LocalDate.now();

        for (Reservation reservation : reservationRepository.findAllByDeviceId(id)) {
            ReservationResponse reservationResponse = ReservationResponse.builder()
                    .reservationId(reservation.getId())
                    .deviceId(reservation.getDeviceId())
                    .userId(reservation.getUserId())
                    .startDate(reservation.getStartDate())
                    .endDate(reservation.getEndDate())
                    .build();

            if (reservation.getEndDate().isBefore(today)) {
                completedReservations.add(reservationResponse);
            } else {
                bookedReservations.add(reservationResponse);
            }

            if ((reservation.getStartDate().isBefore(today) && reservation.getEndDate().isAfter(today)) || reservation.getStartDate().isEqual(today) || reservation.getEndDate().isEqual(today)) {
                available = false;
            }
        }

        DeviceReservation deviceReservation = new DeviceReservation(completedReservations, bookedReservations, available);
        DeviceInformation deviceInformation = new DeviceInformation();

        DeviceDetailResponse deviceDetailResponse = new DeviceDetailResponse(device.getId(), device.getName());
        deviceDetailResponse.setReservations(deviceReservation);
        deviceDetailResponse.setInformation(deviceInformation);

        return deviceDetailResponse;
    }
}

