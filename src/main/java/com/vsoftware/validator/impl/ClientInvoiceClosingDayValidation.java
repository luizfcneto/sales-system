package com.vsoftware.validator.impl;

import com.vsoftware.domain.Client;
import com.vsoftware.exception.InvalidClientDataException;
import com.vsoftware.validator.ValidationStrategy;

public class ClientInvoiceClosingDayValidation implements ValidationStrategy<Client>{

	@Override
	public void validate(Client client) {
		if (client.getInvoiceClosingDay() < 1 || client.getInvoiceClosingDay() > 31) {
            throw new InvalidClientDataException("Erro: Dia de fechamento invalido opcoes sao (1-31).");
        }
	}

}
