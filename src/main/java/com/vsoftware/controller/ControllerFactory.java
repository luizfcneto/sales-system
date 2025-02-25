package com.vsoftware.controller;

import com.vsoftware.dao.ClientDAO;
import com.vsoftware.dao.ProductDAO;
import com.vsoftware.dao.SaleDAO;
import com.vsoftware.dao.SaleItemDAO;
import com.vsoftware.dao.impl.ClientDAOImpl;
import com.vsoftware.dao.impl.ProductDAOImpl;
import com.vsoftware.dao.impl.SaleDAOImpl;
import com.vsoftware.dao.impl.SaleItemDAOImpl;
import com.vsoftware.service.ClientService;
import com.vsoftware.service.ProductService;
import com.vsoftware.service.SaleItemService;
import com.vsoftware.service.SaleReportService;
import com.vsoftware.service.SaleService;
import com.vsoftware.service.impl.ClientServiceImpl;
import com.vsoftware.service.impl.ProductServiceImpl;
import com.vsoftware.service.impl.SaleItemServiceImpl;
import com.vsoftware.service.impl.SaleReportServiceImpl;
import com.vsoftware.service.impl.SaleServiceImpl;
import com.vsoftware.validator.ClientValidatorFactory;
import com.vsoftware.validator.ProductValidatorFactory;
import com.vsoftware.validator.SaleValidatorFactory;
import com.vsoftware.validator.impl.SaleParameterValidator;

public class ControllerFactory {

    private static final ClientDAO clientDAO = new ClientDAOImpl();
    private static final ProductDAO productDAO = new ProductDAOImpl();
    private static final SaleDAO saleDAO = new SaleDAOImpl();
    private static final SaleItemDAO saleItemDAO = new SaleItemDAOImpl();

    private static final ClientService clientService = new ClientServiceImpl(clientDAO);
    private static final ProductService productService = new ProductServiceImpl(productDAO);
    private static final SaleItemService saleItemService = new SaleItemServiceImpl(saleItemDAO);
    private static final SaleValidatorFactory saleValidatorFactory = new SaleValidatorFactory(clientService);
    private static final SaleService saleService = createSaleService();
    private static final SaleReportService saleReportService = new SaleReportServiceImpl(saleDAO);
    private static final SaleParameterValidator saleParameterValidator = new SaleParameterValidator();

    public static ClientController createClientController() {
        return new ClientController(
            clientService,
            ClientValidatorFactory.createValidator(),
            ClientValidatorFactory.updateValidator()
        );
    }

    public static ProductController createProductController() {
        return new ProductController(
            productService,
            ProductValidatorFactory.createValidator(),
            ProductValidatorFactory.updateValidator()
        );
    }

    public static SaleController createSaleController() {
        return new SaleController(
            saleService,
            clientService,
            productService,
            saleReportService,
            saleParameterValidator
        );
    }

    public static SaleItemController createSaleItemController() {
        return new SaleItemController(
            saleService,
            saleItemService,
            productService
        );
    }

    private static SaleService createSaleService() {
        return new SaleServiceImpl(
            saleDAO,
            saleItemDAO,
            saleValidatorFactory.createValidator(), 
            saleValidatorFactory.updateValidator()  
        );
    }
}
