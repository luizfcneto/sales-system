package com.vsoftware.controller;

import java.util.List;

import com.vsoftware.domain.Client;
import com.vsoftware.exception.InvalidClientDataException;
import com.vsoftware.service.ClientService;
import com.vsoftware.utils.ParseUtils;
import com.vsoftware.validator.ValidationStrategy;

public class ClientController {
	private ClientService clientService;
    private final ValidationStrategy<Client> createClientValidator;
    private final ValidationStrategy<Client> updateClientValidator;
	
    public ClientController(ClientService clientService, 
            ValidationStrategy<Client> createValidator, 
            ValidationStrategy<Client> updateValidator) {
		this.clientService = clientService;
		this.createClientValidator = createValidator;
		this.updateClientValidator = updateValidator;
	}

	
    public void createClient(String name, String creditLimitString, String invoiceClosingDayString) {        
        try {
        	double creditLimit = ParseUtils.parseDouble(creditLimitString, "Limite de Credito");
        	int invoiceClosingDay = ParseUtils.parseInt(invoiceClosingDayString, "Dia de Fechamento");
        	
        	Client client = new Client(name, creditLimit, invoiceClosingDay);
        	createClientValidator.validate(client);
        	
        	clientService.createClient(client);
        } catch (Exception ex) {
            throw ex;
        }
        
    }
    
    public void updateClient(Integer clientCode, String name, String creditLimitString, String invoiceClosingDayString) {        
        try {
        	double creditLimit = ParseUtils.parseDouble(creditLimitString, "Limite de Credito");
        	int invoiceClosingDay = ParseUtils.parseInt(invoiceClosingDayString, "Dia de Fechamento");
        	
        	Client client = new Client(clientCode, name, creditLimit, invoiceClosingDay);
        	updateClientValidator.validate(client);
        	
        	clientService.updateClient(client);
        
        } catch (Exception ex) {
            throw ex;
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
            if (code <= 0) {
                throw new InvalidClientDataException("Codigo de cliente inválido, nao pode ser negativo");
            }

            clientService.deleteClient(code);

        } catch (Exception ex) {
            throw ex;
        }
        
    }
 
}
