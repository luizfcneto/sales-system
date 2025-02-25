package com.vsoftware.service;

import java.util.List;

import com.vsoftware.domain.Sale;
import com.vsoftware.domain.SaleItem;

public interface SaleService {
	public void create(Sale sale);
	public Sale getById(int code);
	public List<Sale> getAll();
	public void update(Sale sale);
	public void delete(int code);
	public void addSaleItem(Sale sale, SaleItem item);
	public void removeSaleItem(Sale sale, int productCode);
	
}
