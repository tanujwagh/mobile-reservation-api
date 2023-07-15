package com.example.reservation.exception;

public class NotFoundException extends Exception {
    public NotFoundException(String type, String id) {
        super("%s not found with id - %s".formatted(type, id));
    }
}
