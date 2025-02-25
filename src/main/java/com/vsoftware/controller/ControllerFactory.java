package com.vsoftware.controller;

import com.vsoftware.dao.ClientDAO;
import com.vsoftware.dao.ProductDAO;
import com.vsoftware.dao.impl.ClientDAOImpl;
import com.vsoftware.dao.impl.ProductDAOImpl;
import com.vsoftware.service.ClientService;
import com.vsoftware.service.ProductService;
import com.vsoftware.service.impl.ClientServiceImpl;
import com.vsoftware.service.impl.ProductServiceImpl;
import com.vsoftware.validator.ClientValidatorFactory;
import com.vsoftware.validator.ProductValidatorFactory;

public class ControllerFactory {

	public static ClientController createClientController() {
        ClientDAO clientDAO = new ClientDAOImpl();
        ClientService service = new ClientServiceImpl(clientDAO);
        
        return new ClientController(
            service,
            ClientValidatorFactory.createValidator(),
            ClientValidatorFactory.updateValidator()
        );
    }
	
	public static ProductController createProductController() {
		ProductDAO productDAO = new ProductDAOImpl();
		ProductService service = new ProductServiceImpl(productDAO);
		
		return new ProductController(
				service,
				ProductValidatorFactory.createValidator(),
				ProductValidatorFactory.updateValidator()
		);
	}
}
