package validator;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.vsoftware.domain.Client;
import com.vsoftware.exception.InvalidClientDataException;
import com.vsoftware.validator.impl.ClientInvoiceClosingDayValidation;

public class ClientInvoiceClosingDayValidationTest {
	
	private ClientInvoiceClosingDayValidation validation;

    @BeforeEach
    public void setUp() {
        validation = new ClientInvoiceClosingDayValidation();
    }

    @Test
    @DisplayName("Validar dia de fechamento - válido (1)")
    void testValidarDiaFechamentoValido1() {
        Client client = new Client();
        client.setInvoiceClosingDay(1);
        Assertions.assertDoesNotThrow(() -> validation.validate(client));
    }

    @Test
    @DisplayName("Validar dia de fechamento - válido (31)")
    void testValidarDiaFechamentoValido31() {
        Client client = new Client();
        client.setInvoiceClosingDay(31);
        Assertions.assertDoesNotThrow(() -> validation.validate(client));
    }

    @Test
    @DisplayName("Validar dia de fechamento - inválido (0)")
    void testValidarDiaFechamentoInvalido0() {
        Client client = new Client();
        client.setInvoiceClosingDay(0);
        assertThrows(InvalidClientDataException.class, () -> validation.validate(client));
    }

    @Test
    @DisplayName("Validar dia de fechamento - inválido (32)")
    void testValidarDiaFechamentoInvalido32() {
        Client client = new Client();
        client.setInvoiceClosingDay(32);
        assertThrows(InvalidClientDataException.class, () -> validation.validate(client));
    }

    @Test
    @DisplayName("Validar dia de fechamento - válido (15)")
    void testValidarDiaFechamentoValido15() {
        Client client = new Client();
        client.setInvoiceClosingDay(15);
        Assertions.assertDoesNotThrow(() -> validation.validate(client));
    }

    @Test
    @DisplayName("Validar dia de fechamento - limite inferior (negativo)")
    void testValidarDiaFechamentoLimiteInferiorNegativo() {
        Client client = new Client();
        client.setInvoiceClosingDay(-1);
        assertThrows(InvalidClientDataException.class, () -> validation.validate(client));
    }

    @Test
    @DisplayName("Validar dia de fechamento - limite superior (int máximo)")
    void testValidarDiaFechamentoLimiteSuperiorIntMax() {
        Client client = new Client();
        client.setInvoiceClosingDay(Integer.MAX_VALUE);
        assertThrows(InvalidClientDataException.class, () -> validation.validate(client));
    }
}
