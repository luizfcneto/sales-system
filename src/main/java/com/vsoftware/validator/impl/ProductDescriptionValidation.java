package com.vsoftware.validator.impl;

import com.vsoftware.domain.Product;
import com.vsoftware.exception.InvalidProductDataException;
import com.vsoftware.validator.ValidationStrategy;

public class ProductDescriptionValidation implements ValidationStrategy<Product> {
    @Override
    public void validate(Product product) {
        if (product.getDescription() == null || product.getDescription().trim().isEmpty()) {
            throw new InvalidProductDataException("Erro: Descricao do produto invalida.");
        }
    }
}