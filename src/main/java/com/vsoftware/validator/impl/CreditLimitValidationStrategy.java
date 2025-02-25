package com.vsoftware.validator.impl;

import java.time.LocalDate;

import com.vsoftware.domain.Client;
import com.vsoftware.domain.Sale;
import com.vsoftware.exception.CreditLimitExceededException;
import com.vsoftware.service.ClientService;
import com.vsoftware.utils.DateUtils;
import com.vsoftware.validator.ValidationStrategy;

public class CreditLimitValidationStrategy implements ValidationStrategy<Sale> {
	
    private final ClientService clientService;
    
    public CreditLimitValidationStrategy(ClientService clientService) {
        this.clientService = clientService;
    }
	
	@Override
	public void validate(Sale sale) {
		Client client = sale.getClient();
        LocalDate lastClosing = DateUtils.calculateLastClosing(client.getInvoiceClosingDay(), LocalDate.now());

        double totalSpent = clientService.getTotalSpentSince(client, lastClosing);
        double availableCredit = client.getCreditLimit() - totalSpent;

        if (sale.getTotalValue() > availableCredit) {
            LocalDate nextClosing = DateUtils.calculateNextClosing(client.getInvoiceClosingDay(), LocalDate.now());
            throw new CreditLimitExceededException(
                    "Limite de crédito excedido! Disponível: R$ " + availableCredit +
                            "\nPróximo fechamento: " + nextClosing
            );
        }
	}
	
}
