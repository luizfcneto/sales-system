package com.vsoftware.validator.impl;

import com.vsoftware.domain.Client;
import com.vsoftware.exception.InvalidClientDataException;
import com.vsoftware.validator.ValidationStrategy;

public class ClientCreditLimitValidation implements ValidationStrategy<Client>{

	@Override
	public void validate(Client client) {
		if (client.getCreditLimit() < 0) {
            throw new InvalidClientDataException("Erro: Limite de credito invalido, deve ser positivo.");
        }
	}

}
