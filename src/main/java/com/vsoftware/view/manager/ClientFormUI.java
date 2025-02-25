package com.vsoftware.view.manager;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.vsoftware.domain.Client;

public class ClientFormUI {
    private final JPanel panel;
    private final JTextField txtName;
    private final JTextField txtCreditLimit;
    private final JTextField txtInvoiceClosingDay;
    private final JButton btnAction;

    public ClientFormUI(String actionButtonLabel, Client existingClient) {
        panel = new JPanel(new GridLayout(4, 2, 5, 5));

        txtName = new JTextField(20);
        txtCreditLimit = new JTextField(10);
        txtInvoiceClosingDay = new JTextField(5);
        btnAction = new JButton(actionButtonLabel);

        if (existingClient != null) {
            populateFields(existingClient);
        }

        panel.add(new JLabel("Nome:"));
        panel.add(txtName);
        panel.add(new JLabel("Limite (R$):"));
        panel.add(txtCreditLimit);
        panel.add(new JLabel("Dia Fechamento:"));
        panel.add(txtInvoiceClosingDay);
        panel.add(new JLabel(""));
        panel.add(btnAction);
    }

    private void populateFields(Client client) {
        txtName.setText(client.getName());
        txtCreditLimit.setText(String.valueOf(client.getCreditLimit()));
        txtInvoiceClosingDay.setText(String.valueOf(client.getInvoiceClosingDay()));
    }

    public void addActionListener(ActionListener listener) {
        btnAction.addActionListener(listener);
    }

    public JPanel getFormPanel() {
        return panel;
    }

    public String getName() {
        return txtName.getText().trim();
    }

    public String getCreditLimit() {
        return txtCreditLimit.getText().trim();
    }

    public String getInvoiceClosingDay() {
        return txtInvoiceClosingDay.getText().trim();
    }
}