package com.vsoftware.view.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.vsoftware.domain.Client;

public class ClientTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private List<Client> clients;
    private String[] columnNames = {"Codigo", "Nome", "Limite de Credito", "Dia de Fechamento"};

    public ClientTableModel(List<Client> clients) {
        this.clients = clients;
    }

    @Override
    public int getRowCount() {
        return clients.size();
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
        Client client = clients.get(rowIndex);

        switch (columnIndex) {
            case 0: return client.getCode();
            case 1: return client.getName();
            case 2: return client.getCreditLimit();
            case 3: return client.getInvoiceClosingDay();
            default: return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
        fireTableDataChanged();
    }

}
