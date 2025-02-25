package com.vsoftware.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.vsoftware.domain.Client;
import com.vsoftware.domain.Product;
import com.vsoftware.domain.Sale;
import com.vsoftware.domain.SaleFilter;

public interface SaleReportService {
	Map<Client, Double> getSalesByClient(LocalDate start, LocalDate end);
    Map<Product, Double> getSalesByProduct(LocalDate start, LocalDate end);
    List<Sale> getSalesByFilter(SaleFilter filter);
}
