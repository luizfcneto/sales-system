package com.vsoftware.view.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.vsoftware.domain.Product;

public class ProductTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private List<Product> products;
    private String[] columnNames = {"Codigo", "Descricao", "Preço"};

    public ProductTableModel(List<Product> products) {
        this.products = products;
    }

    @Override
    public int getRowCount() {
        return products.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product product = products.get(rowIndex);

        switch (columnIndex) {
            case 0: return product.getCode();
            case 1: return product.getDescription();
            case 2: return product.getPrice();
            default: return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        fireTableDataChanged();
    }

}
