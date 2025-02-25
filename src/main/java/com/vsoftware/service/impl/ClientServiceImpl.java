package com.vsoftware.service.impl;

import java.time.LocalDate;
import java.util.List;

import com.vsoftware.dao.ClientDAO;
import com.vsoftware.domain.Client;
import com.vsoftware.exception.DatabaseException;
import com.vsoftware.service.ClientService;

public class ClientServiceImpl implements ClientService {
	private ClientDAO clientDAO;

    public ClientServiceImpl(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }
	
	@Override
	public void createClient(Client client) {
		try {
            clientDAO.create(client);
        } catch (DatabaseException e) {
            throw e;
        }		
	}

	@Override
	public void updateClient(Client client) {
		try {
            clientDAO.update(client);
        } catch (DatabaseException e) {
            throw e;
        }		
	}

	@Override
	public List<Client> getAllClients() {
		try {
            return clientDAO.getAll();
        } catch (DatabaseException e) {
            throw e;
        }
	}

	@Override
	public Client getClientByCode(int code) {
		try {
			Client client = clientDAO.getClientByCode(code);
			if (client == null) {
                throw new IllegalArgumentException("Cliente não encontrado.");
            }
			return client;
        } catch (IllegalArgumentException | DatabaseException e) {
            throw e;
        }
	}

	@Override
	public void deleteClient(int code) {
		try {
            clientDAO.delete(code);
        } catch (DatabaseException e) {
            throw e;
        }		
	}

	@Override
	public double getTotalSpentSince(Client client, LocalDate date) {
		try {
            return clientDAO.getTotalSpentSince(client, date);
        } catch (DatabaseException e) {
            throw e;
        }
	}

}
