package com.vsoftware.controller;

import com.vsoftware.dao.ClientDAO;
import com.vsoftware.dao.impl.ClientDAOImpl;
import com.vsoftware.service.ClientService;
import com.vsoftware.service.impl.ClientServiceImpl;
import com.vsoftware.validator.ValidatorFactory;

public class ControllerFactory {

	public static ClientController createClientController() {
        ClientDAO clientDAO = new ClientDAOImpl();
        ClientService service = new ClientServiceImpl(clientDAO);
        
        return new ClientController(
            service,
            ValidatorFactory.createValidator(),
            ValidatorFactory.updateValidator()
        );
    }
}
