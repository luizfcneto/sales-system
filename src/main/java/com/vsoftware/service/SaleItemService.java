package com.vsoftware.service;

import java.util.Map;

import com.vsoftware.domain.SaleItem;

public interface SaleItemService {
	public void create(SaleItem saleItem);
	public SaleItem getByCode(int saleCode, int productCode);
	public Map<Integer, SaleItem> findBySaleCode(int saleCode);
	public void update(SaleItem saleItem);
	public void delete(int saleCode, int productCode);
	public void deleteBySaleCode(int saleCode);
	
}
