package com.vsoftware.service.impl;

import java.util.List;

import com.vsoftware.dao.SaleDAO;
import com.vsoftware.dao.SaleItemDAO;
import com.vsoftware.domain.Sale;
import com.vsoftware.domain.SaleItem;
import com.vsoftware.service.SaleService;
import com.vsoftware.validator.ValidationStrategy;
import com.vsoftware.validator.impl.ItemAdditionValidationStrategy;
import com.vsoftware.validator.impl.UniqueProductValidationStrategy;

public class SaleServiceImpl implements SaleService {
	
	private final SaleDAO saleDAO;
    private final SaleItemDAO saleItemDAO;
    private final ValidationStrategy<Sale> createValidator;
    private final ValidationStrategy<Sale> updateValidator;

    public SaleServiceImpl(
            SaleDAO saleDAO,
            SaleItemDAO saleItemDAO,
            ValidationStrategy<Sale> createValidator,
            ValidationStrategy<Sale> updateValidator
    ) {
        this.saleDAO = saleDAO;
        this.saleItemDAO = saleItemDAO;
        this.createValidator = createValidator;
        this.updateValidator = updateValidator;
    }

    @Override
    public void create(Sale sale) {
        createValidator.validate(sale);
        saleDAO.create(sale);
        sale.getSaleItems().values().forEach(saleItemDAO::create);
    }

    @Override
    public Sale getById(int code) {
        Sale sale = saleDAO.getById(code);
        sale.setSaleItems(saleItemDAO.findBySaleCode(code));
        return sale;
    }

    @Override
    public List<Sale> getAll() {
        List<Sale> sales = saleDAO.getAll();
        sales.forEach(sale -> sale.setSaleItems(saleItemDAO.findBySaleCode(sale.getCode())));
        return sales;
    }

    @Override
    public void update(Sale sale) {
        updateValidator.validate(sale);
        saleDAO.update(sale);
        saleItemDAO.deleteBySaleCode(sale.getCode());
        sale.getSaleItems().values().forEach(saleItemDAO::create);
    }

    @Override
    public void delete(int code) {
        saleItemDAO.deleteBySaleCode(code);
        saleDAO.delete(code);
    }

    @Override
    public void addSaleItem(Sale sale, SaleItem item) {
        ValidationStrategy<SaleItem> itemValidator = new ItemAdditionValidationStrategy();
        itemValidator.validate(item);

        ValidationStrategy<Sale> uniquenessValidator = new UniqueProductValidationStrategy();
        uniquenessValidator.validate(sale);
        checkProductUniqueness(sale, item.getProduct().getCode());
        
        sale.getSaleItems().put(item.getProduct().getCode(), item);
        sale.calculateTotalValue();
        saleItemDAO.create(item);
        saleDAO.update(sale); 
    }

    @Override
    public void removeSaleItem(Sale sale, int productCode) {
        validateSaleAndProduct(sale, productCode);
        
        sale.getSaleItems().remove(productCode);
        sale.calculateTotalValue();
        saleItemDAO.delete(sale.getCode(), productCode);
        saleDAO.update(sale);
    }

    private void checkProductUniqueness(Sale sale, int productCode) {
        if (sale.getSaleItems().containsKey(productCode)) {
            throw new IllegalArgumentException("Produto já existe na venda.");
        }
    }

    private void validateSaleAndProduct(Sale sale, int productCode) {
        if (sale == null) {
            throw new IllegalArgumentException("Venda não pode ser nula.");
        }
        if (!sale.getSaleItems().containsKey(productCode)) {
            throw new IllegalArgumentException("Produto não encontrado na venda.");
        }
    }

}
