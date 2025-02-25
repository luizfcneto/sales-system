package com.vsoftware.validator.impl;

import com.vsoftware.exception.ValidationException;
import com.vsoftware.validator.ValidationStrategy;

public class CodeValueValidationStrategy implements ValidationStrategy<Integer> {
    @Override
    public void validate(Integer code) {
        if (code == null || code <= 0) {
            throw new ValidationException("Código inválido.");
        }
    }
}
