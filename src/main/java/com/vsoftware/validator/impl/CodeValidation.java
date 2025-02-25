package com.vsoftware.validator.impl;

import com.vsoftware.domain.Codeable;
import com.vsoftware.exception.InvalidDataException;
import com.vsoftware.validator.ValidationStrategy;

public class CodeValidation<T extends Codeable> implements ValidationStrategy<T>{

	@Override
	public void validate(T entity) {
		if (entity.getCode() == null || entity.getCode() <= 0) {
			throw new InvalidDataException("Erro: Codigo do cliente invalido, nao pode ser negativo");
		}
	}

}
