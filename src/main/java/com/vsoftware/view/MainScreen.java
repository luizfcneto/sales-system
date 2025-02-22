package com.vsoftware.view;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.vsoftware.controller.ClientController;
import com.vsoftware.domain.Client;
import com.vsoftware.view.client.CreateClientView;
import com.vsoftware.view.client.DeleteClientView;
import com.vsoftware.view.client.ListClientsView;
import com.vsoftware.view.client.UpdateClientView;

public class MainScreen extends JFrame {
	private static final long serialVersionUID = 1L;
    private JDesktopPane desktopPane;

    public MainScreen() {
        setTitle("Sistema de Gerenciamento de Vendas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuCadastro = new JMenu("Clientes");
        menuBar.add(menuCadastro);

        JMenuItem menuItemCreateClient = new JMenuItem("Cadastrar Cliente");
        menuCadastro.add(menuItemCreateClient);
        menuItemCreateClient.addActionListener(e -> {
            CreateClientView createClientView = new CreateClientView();
            ClientController clientController = new ClientController();
            createClientView.setController(clientController);
            clientController.setClientOperationCallback(createClientView);
            desktopPane.add(createClientView);
            centerWindow(createClientView);
        });

        JMenuItem menuItemListClients = new JMenuItem("Listar Clientes");
        menuCadastro.add(menuItemListClients);
        menuItemListClients.addActionListener(e -> {
            ListClientsView listClientsView = new ListClientsView();
            ClientController clientController = new ClientController();
            listClientsView.setController(clientController);
            clientController.setListClientsView(listClientsView);
            desktopPane.add(listClientsView);
            centerWindow(listClientsView);
            listClientsView.updateTableData();
        });

        JMenuItem menuItemUpdateClient = new JMenuItem("Atualizar Cliente");
        menuCadastro.add(menuItemUpdateClient);
        menuItemUpdateClient.addActionListener(e -> {
            ListClientsView listClientsView = new ListClientsView();
            ClientController clientController = new ClientController();
            listClientsView.setController(clientController);
            clientController.setListClientsView(listClientsView);
            desktopPane.add(listClientsView);
            centerWindow(listClientsView);
            listClientsView.updateTableData();

            listClientsView.getTable().addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    int row = listClientsView.getTable().rowAtPoint(evt.getPoint());
                    if (row >= 0) {
                        int code = (int) listClientsView.getTableModel().getValueAt(row, 0);
                        Client client = clientController.getClientByCode(code);
                        UpdateClientView updateClientView = new UpdateClientView();
                        updateClientView.setController(clientController);
                        updateClientView.populateFields(client);
                        clientController.setClientOperationCallback(updateClientView);
                        desktopPane.add(updateClientView);
                        centerWindow(updateClientView);
                    }
                }
            });
        });

        JMenuItem menuItemDeleteClient = new JMenuItem("Remover Cliente");
        menuCadastro.add(menuItemDeleteClient);
        menuItemDeleteClient.addActionListener(e -> {
            ListClientsView listClientsView = new ListClientsView();
            ClientController clientController = new ClientController();
            listClientsView.setController(clientController);
            clientController.setListClientsView(listClientsView);
            desktopPane.add(listClientsView);
            centerWindow(listClientsView);
            listClientsView.updateTableData();

            listClientsView.getTable().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent event) {
                    int row = listClientsView.getTable().rowAtPoint(event.getPoint());
                    if (row >= 0) {
                        int code = (int) listClientsView.getTableModel().getValueAt(row, 0);
                        DeleteClientView deleteClientView = new DeleteClientView(MainScreen.this);
                        deleteClientView.setController(clientController);
                        deleteClientView.setClientCode(code);
                        deleteClientView.setVisible(true);
                    }
                }
            });
        });

        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);

        setVisible(true);
    }

    private void centerWindow(JInternalFrame frame) {
        Dimension desktopSize = desktopPane.getSize();
        Dimension frameSize = frame.getSize();
        frame.setLocation((desktopSize.width - frameSize.width) / 2, (desktopSize.height - frameSize.height) / 2);
        frame.toFront();
        try {
            frame.setSelected(true);
        } catch (PropertyVetoException ex) {
        }
    }

}
