package com.vsoftware.validator.impl;

import java.sql.Date;

import com.vsoftware.exception.ValidationException;
import com.vsoftware.validator.ValidationStrategy;

public class DateValidationStrategy implements ValidationStrategy<Date> {
	
	@Override
    public void validate(Date date) {
        if (date == null) {
            throw new ValidationException("Data da venda é obrigatória.");
        }
    }
}
