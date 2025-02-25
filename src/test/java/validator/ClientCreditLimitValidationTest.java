package validator;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.vsoftware.domain.Client;
import com.vsoftware.exception.InvalidClientDataException;
import com.vsoftware.validator.impl.ClientCreditLimitValidation;

public class ClientCreditLimitValidationTest {
	
	private ClientCreditLimitValidation validation;

    @BeforeEach
    public void setUp() {
        validation = new ClientCreditLimitValidation();
    }

    @Test
    @DisplayName("Validar limite de crédito - válido (0)")
    void testValidarLimiteCreditoValido0() {
        Client client = new Client();
        client.setCreditLimit(0.0);
        Assertions.assertDoesNotThrow(() -> validation.validate(client));
    }

    @Test
    @DisplayName("Validar limite de crédito - válido (positivo)")
    void testValidarLimiteCreditoValidoPositivo() {
        Client client = new Client();
        client.setCreditLimit(1000.0);
        Assertions.assertDoesNotThrow(() -> validation.validate(client));
    }

    @Test
    @DisplayName("Validar limite de crédito - inválido (negativo)")
    void testValidarLimiteCreditoInvalidoNegativo() {
        Client client = new Client();
        client.setCreditLimit(-10.0);
        assertThrows(InvalidClientDataException.class, () -> validation.validate(client));
    }

    @Test
    @DisplayName("Validar limite de crédito - limite positivo")
    void testValidarLimiteCreditoLimitePositivo() {
        Client client = new Client();
        client.setCreditLimit(Double.MAX_VALUE); // Valor máximo de Double
        Assertions.assertDoesNotThrow(() -> validation.validate(client));
    }

    @Test
    @DisplayName("Validar limite de crédito - outro valor positivo")
    void testValidarLimiteCreditoOutroPositivo() {
        Client client = new Client();
        client.setCreditLimit(550.99);
        Assertions.assertDoesNotThrow(() -> validation.validate(client));
    }
}
