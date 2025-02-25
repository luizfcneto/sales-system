package validator;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.vsoftware.domain.Product;
import com.vsoftware.exception.InvalidProductDataException;
import com.vsoftware.validator.impl.ProductPriceValidation;

public class ProductPriceValidationTest {
	private ProductPriceValidation validator;
	
	@BeforeEach
    public void setUp() {
        validator = new ProductPriceValidation();
    }

    @Test
    @DisplayName("Validar preço do produto: Preço válido (maior ou igual a zero)")
    void testValidPrice() {
        Product product = new Product("Produto Teste", 10.0);
        validator.validate(product);
    }

    @Test
    @DisplayName("Validar preço do produto: Preço nulo")
    void testNullPrice() {
        Product product = new Product("Produto Teste", null);

        assertThrows(InvalidProductDataException.class, () -> validator.validate(product),
                "Deveria lançar InvalidProductDataException para preço nulo.");
    }

    @Test
    @DisplayName("Validar preço do produto: Preço negativo")
    void testNegativePrice() {
        Product product = new Product("Produto Teste", -5.0);

        assertThrows(InvalidProductDataException.class, () -> validator.validate(product),
                "Deveria lançar InvalidProductDataException para preço negativo.");
    }

    @Test
    @DisplayName("Validar preço do produto: Preço zero")
    void testZeroPrice() {
        Product product = new Product("Produto Teste", 0.0);
        validator.validate(product);
    }

    @Test
    @DisplayName("Validar preço do produto: Descrição do produto nula")
    void testNullDescription() {
        Product product = new Product(null, 10.0);
        validator.validate(product);
    }
}
