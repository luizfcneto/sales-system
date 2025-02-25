package com.vsoftware.validator;

import java.util.Arrays;

import com.vsoftware.domain.Client;
import com.vsoftware.validator.impl.CodeValidation;
import com.vsoftware.validator.impl.ClientCreditLimitValidation;
import com.vsoftware.validator.impl.ClientInvoiceClosingDayValidation;
import com.vsoftware.validator.impl.NameValidation;

public class ClientValidatorFactory {
	public static ValidationStrategy<Client> createValidator() {
        return new CompositeValidator<>(Arrays.asList(
            new NameValidation<>(),
            new ClientCreditLimitValidation(),
            new ClientInvoiceClosingDayValidation()
        ));
    }

    public static ValidationStrategy<Client> updateValidator() {
        return new CompositeValidator<>(Arrays.asList(
            new CodeValidation<>(),
            new NameValidation<>(),
            new ClientCreditLimitValidation(),
            new ClientInvoiceClosingDayValidation()
        ));
    }
    
}
