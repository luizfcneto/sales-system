package com.vsoftware.view.manager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.vsoftware.controller.ClientController;
import com.vsoftware.domain.Client;
import com.vsoftware.view.WindowManager;
import com.vsoftware.view.model.ClientTableModel;


public class ClientViewManager {

	private ClientController controller;
    private WindowManager windowManager;
    private List<ClientOperationListener> listeners = new ArrayList<>();

    public ClientViewManager(ClientController controller, WindowManager windowManager) {
        this.controller = controller;
        this.windowManager = windowManager;
    }

    public void addClientOperationListener(ClientOperationListener listener) {
        listeners.add(listener);
    }

    public void removeClientOperationListener(ClientOperationListener listener) {
        listeners.remove(listener);
    }

    private void fireClientOperationCompleted() {
        for (ClientOperationListener listener : listeners) {
            listener.onClientOperationCompleted();
        }
    }

    public void showList() {
        try {
            JInternalFrame window = windowManager.createWindow("Lista de Clientes");
            window.setLayout(new BorderLayout());

            JTable table = new JTable();
            updateTable(table);

            JScrollPane scrollPane = new JScrollPane(table);
            window.add(scrollPane, BorderLayout.CENTER);

            window.setPreferredSize(new Dimension(600, 400));
            window.pack();
            windowManager.showWindow(window);

        } catch (Exception e) {
            System.err.println("Erro ao exibir lista de clientes: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao exibir lista de clientes", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateTable(JTable tabela) {
        List<Client> clients = controller.getAllClients();
        ClientTableModel tableModel = new ClientTableModel(clients);
        tabela.setModel(tableModel);
    }

    public void showCreate() {
        try {
            JInternalFrame window = windowManager.createWindow("Cadastrar Cliente");
            window.setLayout(new GridLayout(4, 2, 5, 5));

            JTextField txtName = new JTextField(20);
            JTextField txtCreditLimit = new JTextField(10);
            JTextField txtInvoiceClosingDay = new JTextField(5);

            JButton btnCreate = new JButton("Cadastrar");
            btnCreate.setPreferredSize(new Dimension(120, 30));

            window.add(new JLabel("Nome:"));
            window.add(txtName);
            window.add(new JLabel("Limite:"));
            window.add(txtCreditLimit);
            window.add(new JLabel("Dia Fechamento:"));
            window.add(txtInvoiceClosingDay);
            window.add(btnCreate);

            btnCreate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = txtName.getText();
                    String creditLimit = txtCreditLimit.getText();
                    String invoiceClosingDay = txtInvoiceClosingDay.getText();

                    controller.createClient(name, creditLimit, invoiceClosingDay);

                    txtName.setText("");
                    txtCreditLimit.setText("");
                    txtInvoiceClosingDay.setText("");

                    fireClientOperationCompleted();
                    window.dispose();
                }
            });

            window.setPreferredSize(new Dimension(400, 300));
            window.pack();
            windowManager.showWindow(window);

        } catch (Exception e) {
            System.err.println("Erro ao exibir tela de cadastro: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao exibir tela de cadastro", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showUpdate(int code) {
        try {
            JInternalFrame window = windowManager.createWindow("Editar Cliente");
            window.setLayout(new GridLayout(4, 2, 5, 5));

            Client client = controller.getClientByCode(code);

            JTextField txtName = new JTextField(client.getName(), 20);
            JTextField txtCreditLimit = new JTextField(String.valueOf(client.getCreditLimit()), 10);
            JTextField txtInvoiceClosingDay = new JTextField(String.valueOf(client.getInvoiceClosingDay()), 5);

            JButton btnUpdate = new JButton("Atualizar");
            btnUpdate.setPreferredSize(new Dimension(120, 30));


            window.add(new JLabel("Nome:"));
            window.add(txtName);
            window.add(new JLabel("Limite:"));
            window.add(txtCreditLimit);
            window.add(new JLabel("Dia Fechamento:"));
            window.add(txtInvoiceClosingDay);
            window.add(btnUpdate);

            btnUpdate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = txtName.getText();
                    String creditLimit = txtCreditLimit.getText();
                    String invoiceClosingDay = txtInvoiceClosingDay.getText();

                    controller.updateClient(client.getCode(), name, creditLimit, invoiceClosingDay);

                    fireClientOperationCompleted();
                    window.dispose();
                }
            });

            window.setPreferredSize(new Dimension(400, 300));
            window.pack();
            windowManager.showWindow(window);

        } catch (Exception e) {
            System.err.println("Erro ao exibir tela de edição: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao exibir tela de edição", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showDelete(int code) {
        try {
            JInternalFrame window = windowManager.createWindow("Excluir Cliente");
            window.setLayout(new FlowLayout());

            JLabel message = new JLabel("Deseja realmente excluir o cliente " + code + "?");
            JButton btnConfirm = new JButton("Confirmar");
            JButton btnCancel = new JButton("Cancelar");

            window.add(message);
            window.add(btnConfirm);
            window.add(btnCancel);

            btnConfirm.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controller.deleteClient(code);
                    fireClientOperationCompleted();
                    window.dispose();
                }
            });

            btnCancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    window.dispose();
                }
            });

            window.setPreferredSize(new Dimension(300, 200));
            window.pack();
            windowManager.showWindow(window);

        } catch (Exception e) {
            System.err.println("Erro ao exibir tela de exclusão: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao exibir tela de exclusão", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
