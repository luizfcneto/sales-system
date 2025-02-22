package com.vsoftware.view.client;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.vsoftware.controller.ClientController;
import com.vsoftware.domain.Client;

public class ListClientsView extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private JTable table;
    private DefaultTableModel tableModel;
    private ClientController controller;

    public ListClientsView() {
        super("Clientes Cadastrados", true, true, true, true);
        setSize(600, 400);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"Codigo", "Nome", "Limite de Compra", "Dia de Fechamento da Fatura"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public void setController(ClientController controller) {
        this.controller = controller;
    }

    public void updateTableData() {
        List<Client> clients = controller.getAllClients();
        tableModel.setRowCount(0);
        for (Client client : clients) {
            tableModel.addRow(new Object[]{client.getCode(), client.getName(), client.getCreditLimit(), client.getInvoiceClosingDay()});
        }
    }
    
    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
    
}
