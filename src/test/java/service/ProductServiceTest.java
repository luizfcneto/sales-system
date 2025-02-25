package service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.vsoftware.dao.ProductDAO;
import com.vsoftware.domain.Product;
import com.vsoftware.exception.DatabaseException;
import com.vsoftware.service.impl.ProductServiceImpl;

public class ProductServiceTest {
	
	@Mock
    private ProductDAO productDAO;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImpl(productDAO);
    }
    
    @Test
    @DisplayName("Criar produto: Sucesso")
    void testCreateProductSuccess() {
        Product product = new Product("Produto Teste", 10.0);
        assertDoesNotThrow(() -> productService.createProduct(product));
        verify(productDAO, times(1)).create(product);
    }

    @Test
    @DisplayName("Criar produto: Falha (DatabaseException)")
    void testCreateProductDatabaseException() {
        Product product = new Product("Produto Teste", 10.0);
        doThrow(DatabaseException.class).when(productDAO).create(product);

        assertThrows(DatabaseException.class, () -> productService.createProduct(product));
        verify(productDAO, times(1)).create(product);
    }

    @Test
    @DisplayName("Obter produto por código: Sucesso")
    void testGetProductByCodeSuccess() {
        int code = 1;
        Product product = new Product("Produto Teste", 10.0);
        when(productDAO.getProductByCode(code)).thenReturn(product);

        Product result = productService.getProductByCode(code);

        assertEquals(product, result);
        verify(productDAO, times(1)).getProductByCode(code);
    }

    @Test
    @DisplayName("Obter produto por código: Falha (DatabaseException)")
    void testGetProductByCodeDatabaseException() {
        int code = 1;
        when(productDAO.getProductByCode(code)).thenThrow(DatabaseException.class);

        assertThrows(DatabaseException.class, () -> productService.getProductByCode(code));
        verify(productDAO, times(1)).getProductByCode(code);
    }

    @Test
    @DisplayName("Obter todos os produtos: Sucesso")
    void testGetAllProductsSuccess() {
        List<Product> products = Collections.singletonList(new Product("Produto Teste", 10.0));
        when(productDAO.getAll()).thenReturn(products);

        List<Product> result = productService.getAllProducts();

        assertEquals(products, result);
        verify(productDAO, times(1)).getAll();
    }

    @Test
    @DisplayName("Obter todos os produtos: Falha (DatabaseException)")
    void testGetAllProductsDatabaseException() {
        when(productDAO.getAll()).thenThrow(DatabaseException.class);

        assertThrows(DatabaseException.class, () -> productService.getAllProducts());
        verify(productDAO, times(1)).getAll();
    }

    @Test
    @DisplayName("Atualizar produto: Sucesso")
    void testUpdateProductSuccess() {
        Product product = new Product("Produto Teste", 10.0);
        assertDoesNotThrow(() -> productService.updateProduct(product));
        verify(productDAO, times(1)).update(product);
    }

    @Test
    @DisplayName("Atualizar produto: Falha (DatabaseException)")
    void testUpdateProductDatabaseException() {
        Product product = new Product("Produto Teste", 10.0);
        doThrow(DatabaseException.class).when(productDAO).update(product);

        assertThrows(DatabaseException.class, () -> productService.updateProduct(product));
        verify(productDAO, times(1)).update(product);
    }

    @Test
    @DisplayName("Deletar produto: Sucesso")
    void testDeleteProductSuccess() {
        int code = 1;
        assertDoesNotThrow(() -> productService.deleteProduct(code));
        verify(productDAO, times(1)).delete(code);
    }

    @Test
    @DisplayName("Deletar produto: Falha (DatabaseException)")
    void testDeleteProductDatabaseException() {
        int code = 1;
        doThrow(DatabaseException.class).when(productDAO).delete(code);

        assertThrows(DatabaseException.class, () -> productService.deleteProduct(code));
        verify(productDAO, times(1)).delete(code);
    }
}
