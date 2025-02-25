package com.vsoftware.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.vsoftware.dao.SaleDAO;
import com.vsoftware.domain.Client;
import com.vsoftware.domain.Product;
import com.vsoftware.domain.Sale;
import com.vsoftware.domain.SaleFilter;
import com.vsoftware.service.SaleReportService;
import com.vsoftware.validator.ValidationStrategy;
import com.vsoftware.validator.impl.DateRangeValidationStrategy;

public class SaleReportServiceImpl implements SaleReportService {
	
	private final SaleDAO saleDAO;

    public SaleReportServiceImpl(SaleDAO saleDAO) {
        this.saleDAO = saleDAO;
    }

    @Override
    public Map<Client, Double> getSalesByClient(LocalDate start, LocalDate end) {
    	ValidationStrategy<LocalDate> dateValidator = new DateRangeValidationStrategy();
        dateValidator.validate(start);
        dateValidator.validate(end);
        return saleDAO.getSalesGroupedByClient(start, end);
    }

    @Override
    public Map<Product, Double> getSalesByProduct(LocalDate start, LocalDate end) {
        validateDateRange(start, end);
        return saleDAO.getSalesGroupedByProduct(start, end);
    }

    @Override
    public List<Sale> getSalesByFilter(SaleFilter filter) {
        validateFilter(filter);
        return saleDAO.findByFilter(filter);
    }

    private void validateDateRange(LocalDate start, LocalDate end) {
        if (start == null || end == null || start.isAfter(end)) {
            throw new IllegalArgumentException("Intervalo de datas inválido.");
        }
    }

    private void validateFilter(SaleFilter filter) {
        if (filter == null) throw new IllegalArgumentException("Filtro não pode ser nulo.");
        validateDateRange(filter.getStartDate(), filter.getEndDate());
    }

}
