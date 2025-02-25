package com.vsoftware.validator;

public interface ValidationStrategy<T> {
	void validate(T entity);
}
