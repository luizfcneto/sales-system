package com.vsoftware.validator.impl;

import java.sql.Date;
import java.util.Map;

import com.vsoftware.validator.ValidationStrategy;

public class SaleParameterValidator {
	private final ValidationStrategy<Integer> codeValidator = new CodeValueValidationStrategy();
    private final ValidationStrategy<Date> dateValidator = new DateValidationStrategy();
    private final ValidationStrategy<Map<Integer, Integer>> productValidator = new ProductQuantitiesValidationStrategy();

    public void validateCreateParameters(int clientCode, Date saleDate, Map<Integer, Integer> productQuantities) {
        codeValidator.validate(clientCode);
        dateValidator.validate(saleDate);
        productValidator.validate(productQuantities);
        productQuantities.keySet().forEach(codeValidator::validate);
    }
    
    public void validateUpdateParameters(int clientCode, Date saleDate, Map<Integer, Integer> productQuantities) {
        validateCreateParameters(clientCode, saleDate, productQuantities);
    }
}
