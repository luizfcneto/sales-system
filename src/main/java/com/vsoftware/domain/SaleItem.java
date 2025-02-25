package com.vsoftware.domain;

import java.util.Objects;

public class SaleItem {
	
	private Sale sale;
    private Product product;
    private Integer quantity;

    public SaleItem() {}

    public SaleItem(Sale sale, Product product, Integer quantity) {
        this.sale = sale;
        this.product = product;
        this.quantity = quantity;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleItem saleItem = (SaleItem) o;
        return Objects.equals(sale, saleItem.sale) && Objects.equals(product, saleItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sale, product);
    }

    @Override
    public String toString() {
        return "SaleItem{" +
                "sale=" + (sale != null ? sale.getCode() : null) + 
                ", product=" + (product != null ? product.getCode() : null) + 
                ", quantity=" + quantity +
                '}';
    }
}
