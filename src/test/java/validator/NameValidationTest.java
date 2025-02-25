package validator;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.vsoftware.domain.Nameable;
import com.vsoftware.exception.InvalidClientDataException;
import com.vsoftware.validator.impl.NameValidation;

public class NameValidationTest {
	@Mock
    private Nameable entity;

    private NameValidation<Nameable> nameValidation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        nameValidation = new NameValidation<>();
    }

    @Test
    @DisplayName("Validar nome - nome válido")
    void testValidarNomeValido() {
        when(entity.getName()).thenReturn("Nome Válido");
        nameValidation.validate(entity);
    }

    @Test
    @DisplayName("Validar nome - nome vazio")
    void testValidarNomeVazio() {
        when(entity.getName()).thenReturn("");

        assertThrows(InvalidClientDataException.class, () -> nameValidation.validate(entity));
    }

    @Test
    @DisplayName("Validar nome - nome nulo")
    void testValidarNomeNulo() {
        when(entity.getName()).thenReturn(null);

        assertThrows(InvalidClientDataException.class, () -> nameValidation.validate(entity));
    }

    @Test
    @DisplayName("Validar nome - nome com espaços em branco")
    void testValidarNomeEspacosEmBranco() {
        when(entity.getName()).thenReturn("   ");

        assertThrows(InvalidClientDataException.class, () -> nameValidation.validate(entity));
    }
 
    @Test
    @DisplayName("Validar nome - nome com apenas letras e espaços")
    void testValidarNomeLetrasEspacos() {
        when(entity.getName()).thenReturn("Nome com Espaços");

        nameValidation.validate(entity);
    }

    @Test
    @DisplayName("Validar nome - nome com caracteres especiais")
    void testValidarNomeCaracteresEspeciais() {
        when(entity.getName()).thenReturn("Nome com Caracteres Especiais @#$%");

        nameValidation.validate(entity);
    }
}
