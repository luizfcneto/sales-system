package com.vsoftware.validator.impl;

import com.vsoftware.domain.Product;
import com.vsoftware.exception.InvalidProductDataException;
import com.vsoftware.validator.ValidationStrategy;

public class ProductPriceValidation implements ValidationStrategy<Product> {
    @Override
    public void validate(Product product) {
        if (product.getPrice() == null || product.getPrice() < 0) {
            throw new InvalidProductDataException("Erro: Preco do produto invalido.");
        }
    }
}