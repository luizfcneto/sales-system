package com.vsoftware.domain;

import java.sql.Date;
import java.util.Map;
import java.util.Objects;

public class Sale implements Codeable {
	
	private Integer code;
    private Client client;
    private Date saleDate;
    private Map<Integer, SaleItem> saleItems;
    private Double totalValue;
    
    public Sale() {}
    
    public Sale(Client client, Date saleDate, Map<Integer, SaleItem> saleItems) {
        this.client = client;
        this.saleDate = saleDate;
        this.saleItems = saleItems;
        calculateTotalValue();
    }
    
	@Override
	public Integer getCode() {
		return code;
	}
	
	public void setCode(Integer code) {
        this.code = code;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public Map<Integer, SaleItem> getSaleItems() {
        return saleItems;
    }

    public void setSaleItems(Map<Integer, SaleItem> saleItems) {
        this.saleItems = saleItems;
        calculateTotalValue();
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public void calculateTotalValue() {
        if (saleItems != null) {
            this.totalValue = saleItems.values().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        } else {
            this.totalValue = 0.0;
        }
    }
    

	@Override
	public int hashCode() {
		return Objects.hash(client, code, saleDate, saleItems, totalValue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sale other = (Sale) obj;
		return Objects.equals(client, other.client) && Objects.equals(code, other.code)
				&& Objects.equals(saleDate, other.saleDate) && Objects.equals(saleItems, other.saleItems)
				&& Objects.equals(totalValue, other.totalValue);
	}

	@Override
	public String toString() {
		return "Sale [code=" + code + ", client=" + client + ", saleDate=" + saleDate + ", saleItems=" + saleItems
				+ ", totalValue=" + totalValue + "]";
	}    
    
}
