package com.example.reservation.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;

public class ReservationDateValidator implements ConstraintValidator<ReservationDate, LocalDate> {
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        var today = LocalDate.now();
        return !ObjectUtils.isEmpty(value) && (value.isEqual(today) || value.isAfter(today));
    }
}
