package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.vsoftware.domain.Product;

public class ProductTest {
	
	
	@Test
    @DisplayName("Criar produto: Construtor sem argumentos")
    void testCreateProductNoArgs() {
        Product product = new Product();
        assertNotNull(product);
    }

    @Test
    @DisplayName("Criar produto: Construtor com descrição e preço")
    void testCreateProductDescriptionPrice() {
        Product product = new Product("Produto Teste", 10.0);
        assertEquals("Produto Teste", product.getDescription());
        assertEquals(10.0, product.getPrice());
    }

    @Test
    @DisplayName("Criar produto: Construtor com código, descrição e preço")
    void testCreateProductAllArgs() {
        Product product = new Product(1, "Produto Teste", 10.0);
        assertEquals(1, product.getCode());
        assertEquals("Produto Teste", product.getDescription());
        assertEquals(10.0, product.getPrice());
    }

    @Test
    @DisplayName("Getters e Setters")
    void testGettersAndSetters() {
        Product product = new Product();

        product.setCode(1);
        assertEquals(1, product.getCode());

        product.setDescription("Novo Produto");
        assertEquals("Novo Produto", product.getDescription());

        product.setPrice(20.0);
        assertEquals(20.0, product.getPrice());
    }

    @Test
    @DisplayName("Equals e HashCode: Códigos iguais")
    void testEqualsHashCodeSameCode() {
        Product product1 = new Product(1, "Produto 1", 10.0);
        Product product2 = new Product(1, "Produto 2", 20.0);

        assertEquals(product1, product2);
        assertEquals(product1.hashCode(), product2.hashCode());
    }

    @Test
    @DisplayName("Equals e HashCode: Códigos diferentes")
    void testEqualsHashCodeDifferentCode() {
        Product product1 = new Product(1, "Produto 1", 10.0);
        Product product2 = new Product(2, "Produto 2", 20.0);

        assertNotEquals(product1, product2);
        assertNotEquals(product1.hashCode(), product2.hashCode());
    }

    @Test
    @DisplayName("Equals e HashCode: Comparando com null")
    void testEqualsNull() {
        Product product = new Product(1, "Produto 1", 10.0);
        assertNotEquals(product, null);
    }


    @Test
    @DisplayName("ToString")
    void testToString() {
        Product product = new Product(1, "Produto Teste", 10.0);
        String expectedToString = "Product{code=1, description='Produto Teste', price=10.0}";
        assertEquals(expectedToString, product.toString());
    }
}
