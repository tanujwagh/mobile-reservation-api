package com.example.reservation.exception;

import com.example.reservation.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleReservationNotFoundException(NotFoundException notFoundException, ServletWebRequest request){
        return buildErrorResponse(notFoundException, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(ReservationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleReservationException(ReservationException reservationNotFoundException, ServletWebRequest request){
        return buildErrorResponse(reservationNotFoundException, HttpStatus.BAD_REQUEST, request);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception exception,
                                                      HttpStatus httpStatus,
                                                      ServletWebRequest request) {
        ErrorResponse body =  ErrorResponse.builder()
                .status(httpStatus.value())
                .path(request.getRequest().getRequestURI())
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(httpStatus).body(body);
    }

}