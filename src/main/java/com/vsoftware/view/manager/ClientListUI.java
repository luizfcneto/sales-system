package com.vsoftware.view.manager;

import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.vsoftware.domain.Client;
import com.vsoftware.view.WindowManager;
import com.vsoftware.view.model.ClientTableModel;

public class ClientListUI {
	private final ClientTableModel tableModel;
    private final JTable table;
    private final JScrollPane scrollPane;

    public ClientListUI(List<Client> clients) {
        this.tableModel = new ClientTableModel(clients);
        this.table = new JTable(tableModel);
        configureTable();
        this.scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(WindowManager.LIST_SIZE);
    }

    private void configureTable() {
        table.setAutoCreateRowSorter(true);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public ClientTableModel getTableModel() {
        return tableModel;
    }
}