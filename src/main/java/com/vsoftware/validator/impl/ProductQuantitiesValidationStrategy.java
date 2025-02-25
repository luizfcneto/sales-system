package com.vsoftware.validator.impl;

import java.util.Map;

import com.vsoftware.exception.ValidationException;
import com.vsoftware.validator.ValidationStrategy;

public class ProductQuantitiesValidationStrategy implements ValidationStrategy<Map<Integer, Integer>> {
	@Override
    public void validate(Map<Integer, Integer> quantities) {
        if (quantities == null || quantities.isEmpty()) {
            throw new ValidationException("A venda deve conter pelo menos um produto.");
        }
    }
}
