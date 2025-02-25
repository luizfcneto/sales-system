package com.vsoftware.domain;

import java.util.Objects;

public class Product implements Codeable {
	
	private Integer code;
	private String description;
	private Double price;
		
	public Product() {};
	
	public Product(String description, Double price) {
		this.description = description;
		this.price = price;
	}

	public Product(Integer code, String description, Double price) {
		this.code = code;
		this.description = description;
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@Override
	public Integer getCode() {
		return code;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return code == product.code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "Product{" +
                "code=" + code +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
