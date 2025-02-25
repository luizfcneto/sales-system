package com.vsoftware.service;

import java.util.List;

import com.vsoftware.domain.Product;

public interface ProductService {
	public void createProduct(Product product);
	public Product getProductByCode(int code);
	public List<Product> getAllProducts();
	public void updateProduct(Product product);
	public void deleteProduct(int code);
}
