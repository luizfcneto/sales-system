package com.vsoftware.domain;

import java.util.Objects;

public class Client {
	private Integer code;
    private String name;
    private Double creditLimit;
    private Integer invoiceClosingDay;
    
    public Client() {};
    
    public Client(String name, Double creditLimit, Integer invoiceClosingDay) {
        this.name = name;
        this.creditLimit = creditLimit;
        this.invoiceClosingDay = invoiceClosingDay;
    }
    
	public Client(Integer code, String name, Double creditLimit, Integer invoiceClosingDay) {
		this.code = code;
		this.name = name;
		this.creditLimit = creditLimit;
		this.invoiceClosingDay = invoiceClosingDay;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(Double creditLimit) {
		this.creditLimit = creditLimit;
	}

	public Integer getInvoiceClosingDay() {
		return invoiceClosingDay;
	}

	public void setInvoiceClosingDay(Integer invoiceClosingDay) {
		this.invoiceClosingDay = invoiceClosingDay;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, creditLimit, invoiceClosingDay, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return Objects.equals(code, other.code) && Objects.equals(creditLimit, other.creditLimit)
				&& Objects.equals(invoiceClosingDay, other.invoiceClosingDay) && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Client [code=" + code + ", name=" + name + ", creditLimit=" + creditLimit + ", invoiceClosingDay="
				+ invoiceClosingDay + "]";
	}    
    
}
