package com.example.reservation.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String type, String id) {
        super("%s not found with id - %s".formatted(type, id));
    }
}
