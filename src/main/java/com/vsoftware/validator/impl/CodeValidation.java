package com.vsoftware.validator.impl;

import com.vsoftware.domain.Codeable;
import com.vsoftware.exception.InvalidClientDataException;
import com.vsoftware.validator.ValidationStrategy;

public class CodeValidation<T extends Codeable> implements ValidationStrategy<T>{

	@Override
	public void validate(T entity) {
		if (entity.getCode() <= 0) {
			throw new InvalidClientDataException("Erro: Codigo do cliente invalido, nao pode ser negativo");
		}
	}

}
