package com.vsoftware.validator.impl;

import com.vsoftware.domain.SaleItem;
import com.vsoftware.exception.ValidationException;
import com.vsoftware.validator.ValidationStrategy;

public class ItemAdditionValidationStrategy implements ValidationStrategy<SaleItem> {

	@Override
	public void validate(SaleItem saleItem) {
		if (saleItem.getQuantity() <= 0) {
            throw new ValidationException("Quantidade deve ser positiva");
        }
        
        if (saleItem.getProduct() == null) {
            throw new ValidationException("Produto é obrigatório");
        }		
	}
	
}
