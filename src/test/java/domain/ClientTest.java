package domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.vsoftware.domain.Client;

public class ClientTest {
	
	@Test
    @DisplayName("Teste construtor com parâmetros (nome, limite de crédito, dia de fechamento)")
    void testConstrutorComParametros() {
        Client client = new Client("Cliente Teste", 1000.0, 15);
        assertEquals("Cliente Teste", client.getName());
        assertEquals(1000.0, client.getCreditLimit());
        assertEquals(15, client.getInvoiceClosingDay());
    }

    @Test
    @DisplayName("Teste construtor com todos os parâmetros (código, nome, limite de crédito, dia de fechamento)")
    void testConstrutorComTodosOsParametros() {
        Client client = new Client(1, "Cliente Teste", 1000.0, 15);
        assertEquals(1, client.getCode());
        assertEquals("Cliente Teste", client.getName());
        assertEquals(1000.0, client.getCreditLimit());
        assertEquals(15, client.getInvoiceClosingDay());
    }

    @Test
    @DisplayName("Teste getters e setters")
    void testGettersAndSetters() {
        Client client = new Client();
        client.setCode(1);
        client.setName("Cliente Teste");
        client.setCreditLimit(1000.0);
        client.setInvoiceClosingDay(15);

        assertEquals(1, client.getCode());
        assertEquals("Cliente Teste", client.getName());
        assertEquals(1000.0, client.getCreditLimit());
        assertEquals(15, client.getInvoiceClosingDay());
    }

    @Test
    @DisplayName("Teste hashCode - objetos iguais")
    void testHashCodeObjetosIguais() {
        Client client1 = new Client(1, "Cliente Teste", 1000.0, 15);
        Client client2 = new Client(1, "Cliente Teste", 1000.0, 15);
        assertEquals(client1.hashCode(), client2.hashCode());
    }

    @Test
    @DisplayName("Teste hashCode - objetos diferentes")
    void testHashCodeObjetosDiferentes() {
        Client client1 = new Client(1, "Cliente Teste", 1000.0, 15);
        Client client2 = new Client(2, "Cliente Teste", 1000.0, 15);
        assertNotEquals(client1.hashCode(), client2.hashCode());
    }

    @Test
    @DisplayName("Teste toString")
    void testToString() {
        Client client = new Client(1, "Cliente Teste", 1000.0, 15);
        String expected = "Client [code=1, name=Cliente Teste, creditLimit=1000.0, invoiceClosingDay=15]";
        assertEquals(expected, client.toString());
    }
    
    @Test
    @DisplayName("Teste equals - dois objetos iguais")
    void testEqualsObjetosIguais() {
        Client client1 = new Client(1, "Cliente Teste", 1000.0, 15);
        Client client2 = new Client(1, "Cliente Teste", 1000.0, 15);
        assertEquals(client1, client2);
    }
    
    @Test
    @DisplayName("Teste equals - objeto iguail")
    void testEqualsObjetoIgual() {
        Client client1 = new Client(1, "Cliente Teste", 1000.0, 15);
        assertEquals(client1, client1);
    }

    @Test
    @DisplayName("Teste equals - objetos diferentes")
    void testEqualsObjetosDiferentes() {
        Client client1 = new Client(1, "Cliente Teste", 1000.0, 15);
        Client client2 = new Client(2, "Cliente Teste", 1000.0, 15);
        assertNotEquals(client1, client2);
    }

    @Test
    @DisplayName("Teste equals - comparação com null")
    void testEqualsComparacaoComNull() {
        Client client = new Client(1, "Cliente Teste", 1000.0, 15);
        assertNotEquals(client, null);
    }

    @Test
    @DisplayName("Teste equals - comparação com outro tipo de objeto")
    void testEqualsComparacaoComOutroTipo() {
        Client client = new Client(1, "Cliente Teste", 1000.0, 15);
        assertNotEquals(client, "Outro objeto");
    }
}
