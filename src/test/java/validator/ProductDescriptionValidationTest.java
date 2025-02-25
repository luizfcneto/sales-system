package validator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.vsoftware.domain.Product;
import com.vsoftware.exception.InvalidProductDataException;
import com.vsoftware.validator.impl.ProductDescriptionValidation;

public class ProductDescriptionValidationTest {
	
	private ProductDescriptionValidation validator;

	@BeforeEach
    public void setUp() {
        validator = new ProductDescriptionValidation();
    }
	
    @Test
    @DisplayName("Validar descrição do produto: Descrição válida (não nula e não vazia)")
    void testValidDescription() {
        Product product = new Product("Produto Teste", 10.0);
        assertDoesNotThrow(() -> validator.validate(product));
    }

    @Test
    @DisplayName("Validar descrição do produto: Descrição nula")
    void testNullDescription() {
        Product product = new Product(null, 10.0);

        assertThrows(InvalidProductDataException.class, () -> validator.validate(product),
                "Deveria lançar InvalidProductDataException para descrição nula.");
    }

    @Test
    @DisplayName("Validar descrição do produto: Descrição vazia")
    void testEmptyDescription() {
        Product product = new Product("", 10.0);

        assertThrows(InvalidProductDataException.class, () -> validator.validate(product),
                "Deveria lançar InvalidProductDataException para descrição vazia.");
    }

    @Test
    @DisplayName("Validar descrição do produto: Descrição com apenas espaços em branco")
    void testBlankDescription() {
        Product product = new Product("   ", 10.0);

        assertThrows(InvalidProductDataException.class, () -> validator.validate(product),
                "Deveria lançar InvalidProductDataException para descrição com espaços em branco.");
    }

    @Test
    @DisplayName("Validar descrição do produto: Preço do produto nulo")
    void testNullPrice() {
        Product product = new Product("Produto teste", null);
        assertDoesNotThrow(() -> validator.validate(product));
    }
}
