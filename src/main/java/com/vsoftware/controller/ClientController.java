package com.vsoftware.controller;

import java.util.List;

import javax.swing.JOptionPane;

import com.vsoftware.domain.Client;
import com.vsoftware.service.ClientService;
import com.vsoftware.service.impl.ClientServiceImpl;
import com.vsoftware.view.client.ClientOperationCallback;
import com.vsoftware.view.client.ListClientsView;

public class ClientController {
	private ClientService clientService;
    private ClientOperationCallback callback;
    private ListClientsView listClientsView;
    
    public ClientController() {
        this.clientService = new ClientServiceImpl();
    }
    
    public void setListClientsView(ListClientsView listClientsView) {
        this.listClientsView = listClientsView;
    }
	
    public void createClient(String name, String creditLimitString, String invoiceClosingDayString) {
    	if(name == null || name.isEmpty()) {
    		showErrorMessage("Nome não foi preenchido corretamente. Está vazio ou nulo");
    		return;
    	}
    	
    	double creditLimit;
        try {
            creditLimit = Double.parseDouble(creditLimitString);
            if (creditLimit < 0) {
                showErrorMessage("Limite de compra deve ser positivo.");
                return;
            }
        } catch (NumberFormatException e) {
            showErrorMessage("Formato de limite de compra inválido.");
            return;
        }
        
        int invoiceClosingDay;
        try {
            invoiceClosingDay = Integer.parseInt(invoiceClosingDayString);
            if (invoiceClosingDay < 1 || invoiceClosingDay > 31) {
                showErrorMessage("Dia de fechamento da fatura inválido. Os dias possíveis são: 1-31");
                return;
            }
        } catch (NumberFormatException e) {
            showErrorMessage("Dia de fechamento da fatura inválido.");
            return;
        }
        
        Client client = new Client(name, creditLimit, invoiceClosingDay);
        
        try {
        	clientService.createClient(client);
        	if (callback != null) {
                callback.onClientOperationCompleted();
            }
        	
        } catch (Exception ex) {
        	showErrorMessage("Ocorreu um erro ao tentar cadastrar o cliente: " + client.toString() + " erro: " + ex.getMessage());
        }
        
    }
    
    public void updateClient(int clientCode, String name, String creditLimitString, String invoiceClosingDayString) {
    	if(clientCode <= 0) {
    		showErrorMessage("Código de cliente inválido");
    		return;
    	}
    	
    	if(name == null || name.isEmpty()) {
    		showErrorMessage("Nome não foi preenchido corretamente. Está vazio ou nulo");
    		return;
    	}
    	
    	double creditLimit;
        try {
            creditLimit = Double.parseDouble(creditLimitString);
            if (creditLimit < 0) {
                showErrorMessage("Limite de compra deve ser positivo.");
                return;
            }
        } catch (NumberFormatException e) {
            showErrorMessage("Formato de limite de compra inválido.");
            return;
        }
        
        int invoiceClosingDay;
        try {
            invoiceClosingDay = Integer.parseInt(invoiceClosingDayString);
            if (invoiceClosingDay < 1 || invoiceClosingDay > 31) {
                showErrorMessage("Dia de fechamento da fatura inválido. Os dias possíveis são: 1-31");
                return;
            }
        } catch (NumberFormatException e) {
            showErrorMessage("Dia de fechamento da fatura inválido.");
            return;
        }
        
        Client client = new Client(clientCode, name, creditLimit, invoiceClosingDay);
        
        try {
        	clientService.updateClient(client);
        	if (callback != null) {
                callback.onClientOperationCompleted();
            }
        	
        } catch (Exception ex) {
        	showErrorMessage("Ocorreu um erro ao tentar atualizar o cliente: " + client.toString() + " erro: " + ex.getMessage());
        }    	
    }
    
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    public Client getClientByCode(int code) {
        return clientService.getClientByCode(code);
    }

    public void deleteClient(int code) {
        try {
            clientService.deleteClient(code);
            showSuccessMessage("Cliente removido com sucesso.");
        } catch (Exception e) {
            showErrorMessage("Erro ao tentar remover o cliente com código " + code + " " + e.getMessage());
        }
    }
    
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void setClientOperationCallback(ClientOperationCallback callback) {
        this.callback = callback;
    }
    
    public void updateClientList() {
        if (listClientsView != null) {
            listClientsView.updateTableData();
        }
    }
}
