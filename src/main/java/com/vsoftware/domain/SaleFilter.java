package com.vsoftware.domain;

import java.time.LocalDate;

public class SaleFilter {
	
	private LocalDate startDate;
    private LocalDate endDate;
    private Integer clientCode;
    private Integer productCode;
    
	public LocalDate getStartDate() {
		return startDate;
	}
	
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	public LocalDate getEndDate() {
		return endDate;
	}
	
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Integer getClientCode() {
		return clientCode;
	}
	
	public void setClientCode(Integer clientCode) {
		this.clientCode = clientCode;
	}
	
	public Integer getProductCode() {
		return productCode;
	}
	
	public void setProductCode(Integer productCode) {
		this.productCode = productCode;
	}
        
}
