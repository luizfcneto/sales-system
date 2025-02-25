package com.vsoftware.view.manager;

import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.vsoftware.domain.Product;
import com.vsoftware.view.WindowManager;
import com.vsoftware.view.model.ProductTableModel;

public class ProductListUI {
	private final ProductTableModel tableModel;
    private final JTable table;
    private final JScrollPane scrollPane;

    public ProductListUI(List<Product> products) {
        this.tableModel = new ProductTableModel(products);
        this.table = new JTable(tableModel);
        configureTable();
        this.scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(WindowManager.LIST_SIZE);
    }

    private void configureTable() {
        table.setAutoCreateRowSorter(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public ProductTableModel getTableModel() {
        return tableModel;
    }
}