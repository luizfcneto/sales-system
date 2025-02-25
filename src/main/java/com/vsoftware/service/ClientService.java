package com.vsoftware.service;

import java.util.List;

import com.vsoftware.domain.Client;

public interface ClientService {
	public void createClient(Client client);
	public void updateClient(Client client);
	public List<Client> getAllClients();
	public Client getClientByCode(int code);
	public void deleteClient(int code);
	
}
