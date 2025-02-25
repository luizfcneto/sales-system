package com.vsoftware.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.vsoftware.domain.Client;
import com.vsoftware.domain.Product;
import com.vsoftware.domain.Sale;
import com.vsoftware.domain.SaleFilter;
import com.vsoftware.domain.SaleItem;
import com.vsoftware.service.ClientService;
import com.vsoftware.service.ProductService;
import com.vsoftware.service.SaleReportService;
import com.vsoftware.service.SaleService;
import com.vsoftware.validator.impl.SaleParameterValidator;

public class SaleController {
    private final SaleService saleService;
    private final ClientService clientService;
    private final ProductService productService;
    private final SaleReportService saleReportService;
    private final SaleParameterValidator parameterValidator; 
    
    public SaleController(
            SaleService saleService,
            ClientService clientService,
            ProductService productService,
            SaleReportService saleReportService,
            SaleParameterValidator parameterValidaror
    ) {
        this.saleService = saleService;
        this.clientService = clientService;
        this.productService = productService;
        this.saleReportService = saleReportService;
        this.parameterValidator = parameterValidaror;
    }

    public void createSale(int clientCode, Date saleDate, Map<Integer, Integer> productQuantities) {
    	parameterValidator.validateCreateParameters(clientCode, saleDate, productQuantities);
        Client client = clientService.getClientByCode(clientCode);
        Sale sale = new Sale();
        sale.setClient(client);
        sale.setSaleDate(saleDate);

        buildSaleItems(sale, productQuantities);
        saleService.create(sale);
    }

    public Sale getSaleById(int code) {
        return saleService.getById(code);
    }

    public List<Sale> getAllSales() {
        return saleService.getAll();
    }

    public void updateSale(int saleCode, int clientCode, Date saleDate, Map<Integer, Integer> productQuantities) {
    	parameterValidator.validateUpdateParameters(clientCode, saleDate, productQuantities); 
        
        Sale sale = saleService.getById(saleCode);
        Client client = clientService.getClientByCode(clientCode);
        sale.setClient(client);
        sale.setSaleDate(saleDate);

        buildSaleItems(sale, productQuantities);
        saleService.update(sale);
    }

    public void deleteSale(int code) {
        saleService.delete(code);
    }

    public Map<Client, Double> getSalesGroupedByClient(LocalDate start, LocalDate end) {
        return saleReportService.getSalesByClient(start, end);
    }

    public Map<Product, Double> getSalesGroupedByProduct(LocalDate start, LocalDate end) {
        return saleReportService.getSalesByProduct(start, end);
    }

    public List<Sale> getSalesByFilter(SaleFilter filter) {
        return saleReportService.getSalesByFilter(filter);
    }
    
    private void buildSaleItems(Sale sale, Map<Integer, Integer> productQuantities) {
        productQuantities.forEach((productCode, quantity) -> {
            Product product = productService.getProductByCode(productCode);
            sale.getSaleItems().put(productCode, new SaleItem(sale, product, quantity));
        });
        sale.calculateTotalValue();
    }
}
