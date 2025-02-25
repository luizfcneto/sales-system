package com.vsoftware.validator.impl;

import com.vsoftware.domain.Nameable;
import com.vsoftware.exception.InvalidClientDataException;
import com.vsoftware.validator.ValidationStrategy;

public class NameValidation<T extends Nameable> implements ValidationStrategy<T> {

	@Override
	public void validate(T entity) {
		if (entity.getName() == null || entity.getName().isEmpty()) {
            throw new InvalidClientDataException("Erro: Nome invalido, nao pode ser vazio.");
        }
	}
	
}
