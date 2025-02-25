package validator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.vsoftware.domain.Codeable;
import com.vsoftware.exception.InvalidDataException;
import com.vsoftware.validator.impl.CodeValidation;

public class CodeValidationTest {
	@Mock
    private Codeable entity;

    private CodeValidation<Codeable> codeValidation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        codeValidation = new CodeValidation<>();
    }

    @Test
    @DisplayName("Validar código - código válido (positivo)")
    void testValidarCodigoValidoPositivo() {
        when(entity.getCode()).thenReturn(1);
        codeValidation.validate(entity);
    }

    @Test
    @DisplayName("Validar código - código zero")
    void testValidarCodigoZero() {
        when(entity.getCode()).thenReturn(0);
        assertThrows(InvalidDataException.class, () -> codeValidation.validate(entity));
    }

    @Test
    @DisplayName("Validar código - código negativo")
    void testValidarCodigoNegativo() {
        when(entity.getCode()).thenReturn(-1);
        assertThrows(InvalidDataException.class, () -> codeValidation.validate(entity));
    }

    @Test
    @DisplayName("Validar código - código limite positivo")
    void testValidarCodigoLimitePositivo() {
        when(entity.getCode()).thenReturn(Integer.MAX_VALUE);
        codeValidation.validate(entity);
    }

    @Test
    @DisplayName("Validar código - outro código positivo")
    void testValidarOutroCodigoPositivo() {
        when(entity.getCode()).thenReturn(12345);
        codeValidation.validate(entity);
    }
    
    @Test
    @DisplayName("Validar código - codigo nulo")
    void testValidarCodigoNulo() {
        when(entity.getCode()).thenReturn(null);
        assertThrows(InvalidDataException.class, () -> codeValidation.validate(entity));
    }
}
