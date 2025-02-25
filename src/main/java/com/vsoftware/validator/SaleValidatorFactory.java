package com.vsoftware.validator;

import java.util.Arrays;

import com.vsoftware.domain.Sale;
import com.vsoftware.service.ClientService;
import com.vsoftware.validator.impl.BasicSaleValidationStrategy;
import com.vsoftware.validator.impl.CreditLimitValidationStrategy;
import com.vsoftware.validator.impl.UniqueProductValidationStrategy;

public class SaleValidatorFactory {
	
	private final ClientService clientService;

    public SaleValidatorFactory(ClientService clientService) {
        this.clientService = clientService;
    }

    public ValidationStrategy<Sale> createValidator() {
        return new CompositeValidator<>(
            Arrays.asList(
                new BasicSaleValidationStrategy(), 
                new UniqueProductValidationStrategy(), 
                new CreditLimitValidationStrategy(clientService) 
            )
        );
    }

    public ValidationStrategy<Sale> updateValidator() {
        return new CompositeValidator<>(
            Arrays.asList(
                new BasicSaleValidationStrategy(),
                new CreditLimitValidationStrategy(clientService) 
            )
        );
    }
}
