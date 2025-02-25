package com.vsoftware.validator.impl;

import com.vsoftware.domain.Sale;
import com.vsoftware.exception.ValidationException;
import com.vsoftware.validator.ValidationStrategy;

public class BasicSaleValidationStrategy implements ValidationStrategy<Sale>{

	@Override
	public void validate(Sale sale) {
		if (sale.getClient() == null) {
            throw new ValidationException("Cliente é obrigatório");
        }
        
        if (sale.getSaleDate() == null) {
            throw new ValidationException("Data da venda é obrigatória");
        }
        
        if (sale.getSaleItems() == null || sale.getSaleItems().isEmpty()) {
            throw new ValidationException("A venda deve conter itens");
        }
	}
	
}
