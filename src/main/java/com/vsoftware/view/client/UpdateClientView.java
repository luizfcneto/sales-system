package com.vsoftware.view.client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.vsoftware.controller.ClientController;
import com.vsoftware.domain.Client;

public class UpdateClientView extends JInternalFrame implements ClientOperationCallback {

	private static final long serialVersionUID = 1L;
	
	private JTextField txtName;
    private JTextField txtCreditLimit;
    private JTextField txtInvoiceClosingDay;
    private JButton btnUpdate;
    private ClientController controller;
    private int clientCode;

    public UpdateClientView() {
        super("Atualizar Cliente", true, true, true, true); 
        setSize(400, 300);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 5, 5));

        add(new JLabel("Nome:"));
        txtName = new JTextField();
        add(txtName);

        add(new JLabel("Limite de compra:"));
        txtCreditLimit = new JTextField();
        add(txtCreditLimit);

        add(new JLabel("Dia de Fechamento da fatura (1-31):"));
        txtInvoiceClosingDay = new JTextField();
        add(txtInvoiceClosingDay);

        btnUpdate = new JButton("Atualizar");
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = txtName.getText();
                String creditLimit = txtCreditLimit.getText();
                String invoiceClosingDay = txtInvoiceClosingDay.getText();

                controller.updateClient(clientCode, name, creditLimit, invoiceClosingDay);
            }
        });
        add(btnUpdate);

        setVisible(true);
    }

    public void setController(ClientController controller) {
        this.controller = controller;
    }

    public void populateFields(Client client) {
        txtName.setText(client.getName());
        txtCreditLimit.setText(String.valueOf(client.getCreditLimit()));
        txtInvoiceClosingDay.setText(String.valueOf(client.getInvoiceClosingDay()));
        clientCode = client.getCode();
    }

    @Override
    public void onClientOperationCompleted() {
        JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
        
        if (controller != null) {
            controller.updateClientList();
        }
    }
	
}
