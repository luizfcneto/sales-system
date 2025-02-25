package com.vsoftware.validator.impl;

import com.vsoftware.dao.SaleItemDAO;
import com.vsoftware.domain.SaleItem;
import com.vsoftware.exception.ValidationException;
import com.vsoftware.validator.ValidationStrategy;

public class SaleItemExistenceValidationStrategy implements ValidationStrategy<SaleItem> {
	private final SaleItemDAO saleItemDAO;

    public SaleItemExistenceValidationStrategy(SaleItemDAO saleItemDAO) {
        this.saleItemDAO = saleItemDAO;
    }

    @Override
    public void validate(SaleItem item) {
        SaleItem existing = saleItemDAO.getByCode(item.getSale().getCode(), item.getProduct().getCode());
        if (existing != null) {
            throw new ValidationException("Produto já está na venda.");
        }
    }
}
