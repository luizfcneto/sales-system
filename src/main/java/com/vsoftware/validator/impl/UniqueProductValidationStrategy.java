package com.vsoftware.validator.impl;

import java.util.Map;

import com.vsoftware.domain.Sale;
import com.vsoftware.domain.SaleItem;
import com.vsoftware.exception.UniqueProductValidationSaleException;
import com.vsoftware.validator.ValidationStrategy;

public class UniqueProductValidationStrategy implements ValidationStrategy<Sale> {
		
	@Override
	public void validate(Sale sale) {
		Map<Integer, SaleItem> items = sale.getSaleItems();
        long uniqueCount = items.values().stream()
            .map(item -> item.getProduct().getCode())
            .distinct()
            .count();

        if (uniqueCount != items.size()) {
            throw new UniqueProductValidationSaleException("Produtos duplicados na venda!");
        }		
	}

}
