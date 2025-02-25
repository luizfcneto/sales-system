package com.vsoftware.service.impl;

import java.util.List;

import com.vsoftware.dao.ProductDAO;
import com.vsoftware.domain.Product;
import com.vsoftware.exception.DatabaseException;
import com.vsoftware.service.ProductService;

public class ProductServiceImpl implements ProductService {
	
	private final ProductDAO productDAO;
	
	public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
	
	@Override
	public void createProduct(Product product) {
		try {
            productDAO.create(product);
        } catch (DatabaseException e) {
            throw e;
        }
	}

	@Override
	public Product getProductByCode(int code) {
		try {
			Product product = productDAO.getProductByCode(code);
			if (product == null) {
                throw new IllegalArgumentException("Produto não encontrado.");
            }
			return product;
        } catch (IllegalArgumentException | DatabaseException e) {
            throw e;
        }
	}

	@Override
	public List<Product> getAllProducts() {
		try {
            return productDAO.getAll();
        } catch (DatabaseException e) {
            throw e;
        }
	}

	@Override
	public void updateProduct(Product product) {
		try {
            productDAO.update(product);
        } catch (DatabaseException e) {
            throw e;
        }		
	}

	@Override
	public void deleteProduct(int code) {
		try {
            productDAO.delete(code);
        } catch (DatabaseException e) {
            throw e;
        }		
	}
	
}
