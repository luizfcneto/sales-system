package com.vsoftware.service.impl;

import java.util.Arrays;
import java.util.Map;

import com.vsoftware.dao.SaleItemDAO;
import com.vsoftware.domain.SaleItem;
import com.vsoftware.service.SaleItemService;
import com.vsoftware.validator.CompositeValidator;
import com.vsoftware.validator.ValidationStrategy;
import com.vsoftware.validator.impl.ItemAdditionValidationStrategy;
import com.vsoftware.validator.impl.SaleItemExistenceValidationStrategy;

public class SaleItemServiceImpl implements SaleItemService {

	private final SaleItemDAO saleItemDAO;

    public SaleItemServiceImpl(SaleItemDAO saleItemDAO) {
        this.saleItemDAO = saleItemDAO;
    }

    @Override
    public void create(SaleItem saleItem) {
    	ValidationStrategy<SaleItem> itemValidator = new CompositeValidator<>(
    			Arrays.asList(
                    new ItemAdditionValidationStrategy(),
                    new SaleItemExistenceValidationStrategy(saleItemDAO)
                )
            );
            itemValidator.validate(saleItem);
        saleItemDAO.create(saleItem);
    }

    @Override
    public SaleItem getByCode(int saleCode, int productCode) {
        return saleItemDAO.getByCode(saleCode, productCode);
    }

    @Override
    public Map<Integer, SaleItem> findBySaleCode(int saleCode) {
        return saleItemDAO.findBySaleCode(saleCode);
    }

    @Override
    public void update(SaleItem saleItem) {
        saleItemDAO.update(saleItem);
    }

    @Override
    public void delete(int saleCode, int productCode) {
        saleItemDAO.delete(saleCode, productCode);
    }

    @Override
    public void deleteBySaleCode(int saleCode) {
        saleItemDAO.deleteBySaleCode(saleCode);
    }

}
