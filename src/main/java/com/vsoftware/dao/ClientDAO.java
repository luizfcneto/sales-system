package com.vsoftware.dao;

import java.util.List;

import com.vsoftware.domain.Client;

public interface ClientDAO {
	
	public void create(Client client);
	public void update(Client client);
	public List<Client> getAll();
	public Client getClientByCode(int code);
	public void delete(int code);
	
}
