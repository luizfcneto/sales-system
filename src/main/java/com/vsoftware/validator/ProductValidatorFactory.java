package com.vsoftware.validator;

import java.util.Arrays;

import com.vsoftware.domain.Product;
import com.vsoftware.validator.impl.CodeValidation;
import com.vsoftware.validator.impl.ProductDescriptionValidation;
import com.vsoftware.validator.impl.ProductPriceValidation;

public class ProductValidatorFactory {
	public static ValidationStrategy<Product> createValidator() {
        return new CompositeValidator<>(Arrays.asList(
                new ProductDescriptionValidation(),
                new ProductPriceValidation()
        ));
    }

    public static ValidationStrategy<Product> updateValidator() {
        return new CompositeValidator<>(Arrays.asList(
                new CodeValidation<>(), 
                new ProductDescriptionValidation(),
                new ProductPriceValidation()
        ));
    }
}
