package com.vsoftware.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.vsoftware.domain.Client;
import com.vsoftware.domain.Product;
import com.vsoftware.domain.Sale;
import com.vsoftware.domain.SaleFilter;

public interface SaleDAO {

	public void create(Sale sale);
	public Sale getById(int code);
	public List<Sale> getAll();
	public void update(Sale sale);
	public void delete(int code);
	Map<Client, Double> getSalesGroupedByClient(LocalDate start, LocalDate end);
    Map<Product, Double> getSalesGroupedByProduct(LocalDate start, LocalDate end);
    List<Sale> findByFilter(SaleFilter filter);
	
}
