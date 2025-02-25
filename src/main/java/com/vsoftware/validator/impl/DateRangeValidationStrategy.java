package com.vsoftware.validator.impl;

import java.time.LocalDate;

import com.vsoftware.exception.ValidationException;
import com.vsoftware.validator.ValidationStrategy;

public class DateRangeValidationStrategy implements ValidationStrategy<LocalDate> {
    @Override
    public void validate(LocalDate date) {
        if (date == null) {
            throw new ValidationException("Data não pode ser nula.");
        }
    }
}
