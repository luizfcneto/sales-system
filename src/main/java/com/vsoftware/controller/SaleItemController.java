package com.vsoftware.controller;

import com.vsoftware.domain.Product;
import com.vsoftware.domain.Sale;
import com.vsoftware.domain.SaleItem;
import com.vsoftware.service.ProductService;
import com.vsoftware.service.SaleItemService;
import com.vsoftware.service.SaleService;
import com.vsoftware.validator.ValidationStrategy;
import com.vsoftware.validator.impl.CodeValueValidationStrategy;
import com.vsoftware.validator.impl.QuantityValidationStrategy;

public class SaleItemController {

	private final SaleService saleService;
    private final SaleItemService saleItemService;
    private final ProductService productService;

    public SaleItemController(
            SaleService saleService,
            SaleItemService saleItemService,
            ProductService productService
    ) {
        this.saleService = saleService;
        this.saleItemService = saleItemService;
        this.productService = productService;
    }

    public void addSaleItem(int saleCode, int productCode, int quantity) {
    	ValidationStrategy<Integer> productCodeValidator = new CodeValueValidationStrategy();
        productCodeValidator.validate(productCode);

        ValidationStrategy<Integer> quantityValidator = new QuantityValidationStrategy();
        quantityValidator.validate(quantity);
        
        Sale sale = saleService.getById(saleCode);
        Product product = productService.getProductByCode(productCode);
        SaleItem saleItem = new SaleItem(sale, product, quantity);
        saleItemService.create(saleItem);
        sale.calculateTotalValue();
        saleService.update(sale);
    }

    public void updateSaleItem(int saleCode, int productCode, int quantity) {
        SaleItem saleItem = saleItemService.getByCode(saleCode, productCode);
        saleItem.setQuantity(quantity);
        saleItemService.update(saleItem);
        Sale sale = saleService.getById(saleCode);
        sale.calculateTotalValue();
        saleService.update(sale);
    }

    public void removeSaleItem(int saleCode, int productCode) {
        saleItemService.delete(saleCode, productCode);
        Sale sale = saleService.getById(saleCode);
        sale.calculateTotalValue();
        saleService.update(sale);
    }
	
}
