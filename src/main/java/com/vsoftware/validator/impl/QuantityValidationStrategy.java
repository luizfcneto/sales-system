package com.vsoftware.validator.impl;

import com.vsoftware.exception.ValidationException;
import com.vsoftware.validator.ValidationStrategy;

public class QuantityValidationStrategy implements ValidationStrategy<Integer>{
	
	@Override
    public void validate(Integer quantity) {
        if (quantity <= 0) {
            throw new ValidationException("Quantidade deve ser positiva.");
        }
    }
}
