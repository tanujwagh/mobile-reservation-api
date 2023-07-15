package com.example.reservation.controller;

import com.example.reservation.dto.request.ReservationRequest;
import com.example.reservation.dto.response.PageResponse;
import com.example.reservation.dto.response.ReservationResponse;
import com.example.reservation.exception.ApiException;
import com.example.reservation.exception.NotFoundException;
import com.example.reservation.exception.ReservationException;
import com.example.reservation.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public PageResponse<ReservationResponse> getAllReservations(@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "20") Integer size) {
        return reservationService.getAllReservations(PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "startDateTime")));
    }

    @GetMapping("/{id}")
    public ReservationResponse getReservationById(@PathVariable("id") String id) throws NotFoundException {
        return reservationService.getReservation(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse createReservation(@Valid @RequestBody ReservationRequest request) throws ReservationException {
        return reservationService.createReservation(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ReservationResponse updateReservation(@PathVariable("id") String id, @Valid @RequestBody ReservationRequest request) {
        throw new ApiException("Not supported");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteReservation(@PathVariable("id") String id) throws NotFoundException {
        reservationService.deleteReservation(id);
    }
}
