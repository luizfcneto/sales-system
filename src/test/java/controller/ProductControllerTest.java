package controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.vsoftware.controller.ProductController;
import com.vsoftware.domain.Product;
import com.vsoftware.exception.InvalidProductDataException;
import com.vsoftware.service.ProductService;
import com.vsoftware.validator.ValidationStrategy;

public class ProductControllerTest {
	
	@Mock
    private ProductService productService;

    @Mock
    private ValidationStrategy<Product> createProductValidator;

    @Mock
    private ValidationStrategy<Product> updateProductValidator;

    private ProductController productController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productController = new ProductController(productService, createProductValidator, updateProductValidator);
    }

    @Test
    @DisplayName("Criar produto: Sucesso")
    void testCreateProductSuccess() {
        String description = "Produto Teste";
        String priceString = "10.0";

        assertDoesNotThrow(() -> productController.createProduct(description, priceString));

        verify(createProductValidator, times(1)).validate(any(Product.class));
        verify(productService, times(1)).createProduct(any(Product.class));
    }

    @Test
    @DisplayName("Criar produto: Falha (Preço inválido - NumberFormatException)")
    void testCreateProductInvalidPriceFormat() {
        String description = "Produto Teste";
        String priceString = "abc";

        assertThrows(NumberFormatException.class, () -> productController.createProduct(description, priceString));

        verify(createProductValidator, times(0)).validate(any(Product.class));
        verify(productService, times(0)).createProduct(any(Product.class));
    }

    @Test
    @DisplayName("Criar produto: Falha (Validação lança InvalidProductDataException)")
    void testCreateProductInvalidProductData() {
        String description = "Produto Teste";
        String priceString = "10.0";
        Product product = new Product(description, 10.0);

        doThrow(InvalidProductDataException.class).when(createProductValidator).validate(product);

        assertThrows(InvalidProductDataException.class, () -> productController.createProduct(description, priceString));

        verify(createProductValidator, times(1)).validate(any(Product.class));
        verify(productService, times(0)).createProduct(any(Product.class));
    }

    @Test
    @DisplayName("Obter produto por código")
    void testGetProductByCode() {
        int code = 1;
        Product product = new Product("Produto Teste", 10.0);
        when(productService.getProductByCode(code)).thenReturn(product);

        Product result = productController.getProductByCode(code);

        assertEquals(product, result);
        verify(productService, times(1)).getProductByCode(code);
    }

    @Test
    @DisplayName("Obter todos os produtos")
    void testGetAllProducts() {
        List<Product> products = Collections.singletonList(new Product("Produto Teste", 10.0));
        when(productService.getAllProducts()).thenReturn(products);

        List<Product> result = productController.getAllProducts();

        assertEquals(products, result);
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    @DisplayName("Atualizar produto: Sucesso")
    void testUpdateProductSuccess() {
        int code = 1;
        String description = "Produto Teste Atualizado";
        String priceString = "20.0";

        assertDoesNotThrow(() -> productController.updateProduct(code, description, priceString));

        verify(updateProductValidator, times(1)).validate(any(Product.class));
        verify(productService, times(1)).updateProduct(any(Product.class));
    }

    @Test
    @DisplayName("Atualizar produto: Falha (Validação lança InvalidProductDataException)")
    void testUpdateProductInvalidProductData() {
        int code = 1;
        String description = "Produto Teste Atualizado";
        String priceString = "20.0";
        Product product = new Product(code, description, 20.0);

        doThrow(InvalidProductDataException.class).when(updateProductValidator).validate(product);

        assertThrows(InvalidProductDataException.class, () -> productController.updateProduct(code, description, priceString));

        verify(updateProductValidator, times(1)).validate(any(Product.class));
        verify(productService, times(0)).updateProduct(any(Product.class));
    }

    @Test
    @DisplayName("Deletar produto")
    void testDeleteProduct() {
        int code = 1;
        assertDoesNotThrow(() -> productController.deleteProduct(code));
        verify(productService, times(1)).deleteProduct(code);
    }
}
