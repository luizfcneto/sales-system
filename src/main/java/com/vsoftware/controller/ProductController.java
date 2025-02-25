package com.vsoftware.controller;

import java.util.List;

import com.vsoftware.domain.Product;
import com.vsoftware.exception.InvalidDataException;
import com.vsoftware.exception.InvalidProductDataException;
import com.vsoftware.service.ProductService;
import com.vsoftware.utils.ParseUtils;
import com.vsoftware.validator.ValidationStrategy;

public class ProductController {
	private final ProductService productService;
    private final ValidationStrategy<Product> createProductValidator;
    private final ValidationStrategy<Product> updateProductValidator;

    public ProductController(
    		ProductService productService, 
    		ValidationStrategy<Product> createProductValidator, 
    		ValidationStrategy<Product> updateProductValidator) {
        this.productService = productService;
        this.createProductValidator = createProductValidator;
        this.updateProductValidator = updateProductValidator;
    }

    public void createProduct(String description, String priceString) {
        try {
            double price = ParseUtils.parseDouble(priceString, "Preço");
            Product product = new Product(description, price);
            createProductValidator.validate(product);
            productService.createProduct(product);
        } catch (InvalidDataException | InvalidProductDataException | NumberFormatException ex) {
            throw ex;
        }
    }

    public Product getProductByCode(int code) {
        return productService.getProductByCode(code);
    }

    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    public void updateProduct(int code, String description, String priceString) {
        try {
            double price = ParseUtils.parseDouble(priceString, "Preço");
            Product product = new Product(code, description, price);
            updateProductValidator.validate(product);
            productService.updateProduct(product);
        } catch (InvalidDataException | InvalidProductDataException | NumberFormatException ex) {
            throw ex;
        }
    }

    public void deleteProduct(int code) {
        productService.deleteProduct(code);
    }
}
