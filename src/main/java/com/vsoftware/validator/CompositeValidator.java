package com.vsoftware.validator;

import java.util.List;

public class CompositeValidator<T> implements ValidationStrategy<T> {
	private final List<ValidationStrategy<T>> strategies;
	
	public CompositeValidator(List<ValidationStrategy<T>> strategies) {
		this.strategies = strategies;
	}
	
	@Override
	public void validate(T entity) {
		for(ValidationStrategy<T> strategy : strategies) {
			strategy.validate(entity);
		}
	}

}
