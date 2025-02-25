package com.vsoftware.dao;

import java.util.List;

import com.vsoftware.domain.Product;

public interface ProductDAO {
	public void create(Product product);
	public void update(Product product);
	public List<Product> getAll();
	public Product getProductByCode(int code);
	public void delete(int code);
	
}
