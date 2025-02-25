package com.vsoftware.view.manager;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.vsoftware.controller.ClientController;
import com.vsoftware.domain.Client;
import com.vsoftware.view.WindowManager;


public class ClientViewManager {

	private final ClientController controller;
    private final WindowManager windowManager;
    private final List<ClientOperationListener> listeners = new ArrayList<>();

    public ClientViewManager(ClientController controller, WindowManager windowManager) {
        this.controller = controller;
        this.windowManager = windowManager;
    }

    public void addClientOperationListener(ClientOperationListener listener) {
        listeners.add(listener);
    }

    private void fireClientOperationCompleted() {
        listeners.forEach(ClientOperationListener::onClientOperationCompleted);
    }

    public void showList() {
        JInternalFrame window = windowManager.createWindow("Lista de Clientes");
        ClientListUI listUI = new ClientListUI(controller.getAllClients());
        window.add(listUI.getScrollPane());
        window.pack();
        windowManager.showWindow(window);
    }

    public void updateExistingList(JInternalFrame listWindow) {
        JScrollPane scrollPane = (JScrollPane) listWindow.getContentPane().getComponent(0);
        JTable table = (JTable) scrollPane.getViewport().getView();
        ClientListUI listUI = new ClientListUI(controller.getAllClients());
        table.setModel(listUI.getTableModel());
    }

    public void showCreate() {
        JInternalFrame window = windowManager.createWindow("Cadastrar Cliente");
        ClientFormUI formUI = new ClientFormUI("Cadastrar", null);
        formUI.addActionListener(e -> handleFormSubmission(window, formUI));
        window.add(formUI.getFormPanel());
        window.pack();
        windowManager.showWindow(window);
    }

    public void showUpdate(int code) {
        Client client = controller.getClientByCode(code);
        JInternalFrame window = windowManager.createWindow("Editar Cliente - " + code);
        ClientFormUI formUI = new ClientFormUI("Atualizar", client);
        formUI.addActionListener(e -> handleUpdateSubmission(window, formUI, client.getCode()));
        window.add(formUI.getFormPanel());
        window.pack();
        windowManager.showWindow(window);
    }

    public void showDelete(int code) {
        JInternalFrame window = windowManager.createWindow("Excluir Cliente");
        int option = JOptionPane.showConfirmDialog(
            window,
            "Deseja realmente excluir o cliente " + code + "?",
            "Confirmação",
            JOptionPane.YES_NO_OPTION
        );

        if (option == JOptionPane.YES_OPTION) {
            try {
                controller.deleteClient(code);
                fireClientOperationCompleted();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    window,
                    ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
        window.dispose();
    }

    private void handleFormSubmission(JInternalFrame window, ClientFormUI formUI) {
        try {
            controller.createClient(
                formUI.getName(),
                formUI.getCreditLimit(),
                formUI.getInvoiceClosingDay()
            );
            fireClientOperationCompleted();
            window.dispose();
        } catch (Exception ex) {
            showErrorMessage(window, ex);
        }
    }

    private void handleUpdateSubmission(JInternalFrame window, ClientFormUI formUI, int code) {
        try {
            controller.updateClient(
                code,
                formUI.getName(),
                formUI.getCreditLimit(),
                formUI.getInvoiceClosingDay()
            );
            fireClientOperationCompleted();
            window.dispose();
        } catch (Exception ex) {
            showErrorMessage(window, ex);
        }
    }

    private void showErrorMessage(JInternalFrame window, Exception ex) {
        JOptionPane.showMessageDialog(
            window,
            ex.getMessage(),
            "Erro na Operação",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
