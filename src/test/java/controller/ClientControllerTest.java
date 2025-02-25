package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.vsoftware.controller.ClientController;
import com.vsoftware.domain.Client;
import com.vsoftware.exception.InvalidClientDataException;
import com.vsoftware.service.ClientService;
import com.vsoftware.validator.ValidationStrategy;

public class ClientControllerTest {
	@Mock
    private ClientService clientService;
    @Mock
    private ValidationStrategy<Client> createClientValidator;
    @Mock
    private ValidationStrategy<Client> updateClientValidator;

    private ClientController clientController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        clientController = new ClientController(clientService, createClientValidator, updateClientValidator);
    }

    @Test
    @DisplayName("Criar cliente - sucesso")
    void testCreateClientSucesso() {
        String name = "Cliente Teste";
        String creditLimit = "1000.0";
        String invoiceClosingDay = "15";

        clientController.createClient(name, creditLimit, invoiceClosingDay);

        verify(createClientValidator).validate(any(Client.class));
        verify(clientService).createClient(any(Client.class));
    }

    @Test
    @DisplayName("Criar cliente - limite de crédito inválido")
    void testCreateClientLimiteCreditoInvalido() {
        String name = "Cliente Teste";
        String creditLimit = "abc";
        String invoiceClosingDay = "15";

        assertThrows(NumberFormatException.class, () -> clientController.createClient(name, creditLimit, invoiceClosingDay));
    }

    @Test
    @DisplayName("Criar cliente - dia de fechamento inválido")
    void testCreateClientDiaFechamentoInvalido() {
        String name = "Cliente Teste";
        String creditLimit = "1000.0";
        String invoiceClosingDay = "xyz";

        assertThrows(NumberFormatException.class, () -> clientController.createClient(name, creditLimit, invoiceClosingDay));
    }

    @Test
    @DisplayName("Criar cliente - validação lança exceção")
    void testCreateClientValidacaoLancandoExcecao() {
        String name = "Cliente Teste";
        String creditLimit = "1000.0";
        String invoiceClosingDay = "15";
        Client client = new Client(name, Double.parseDouble(creditLimit), Integer.parseInt(invoiceClosingDay));

        doThrow(InvalidClientDataException.class).when(createClientValidator).validate(client);

        assertThrows(InvalidClientDataException.class, () -> clientController.createClient(name, creditLimit, invoiceClosingDay));
    }


    @Test
    @DisplayName("Atualizar cliente - sucesso")
    void testUpdateClientSucesso() {
        Integer code = 1;
        String name = "Cliente Teste";
        String creditLimit = "2000.0";
        String invoiceClosingDay = "20";

        clientController.updateClient(code, name, creditLimit, invoiceClosingDay);

        verify(updateClientValidator).validate(any(Client.class));
        verify(clientService).updateClient(any(Client.class));
    }
 
    @Test
    @DisplayName("Atualizar cliente - código inválido")
    void testUpdateClientCodigoInvalido() {
        Integer code = null;
        String name = "Cliente Teste";
        String creditLimit = "2000.0";
        String invoiceClosingDay = "20";

        doThrow(InvalidClientDataException.class).when(updateClientValidator).validate(any(Client.class));

        assertThrows(InvalidClientDataException.class, () -> clientController.updateClient(code, name, creditLimit, invoiceClosingDay));
    }


    @Test
    @DisplayName("Atualizar cliente - limite de crédito inválido")
    void testUpdateClientLimiteCreditoInvalido() {
        Integer code = 1;
        String name = "Cliente Teste";
        String creditLimit = "abc";
        String invoiceClosingDay = "20";

        assertThrows(NumberFormatException.class, () -> clientController.updateClient(code, name, creditLimit, invoiceClosingDay));
    }

    @Test
    @DisplayName("Atualizar cliente - dia de fechamento inválido")
    void testUpdateClientDiaFechamentoInvalido() {
        Integer code = 1;
        String name = "Cliente Teste";
        String creditLimit = "2000.0";
        String invoiceClosingDay = "xyz";

        assertThrows(NumberFormatException.class, () -> clientController.updateClient(code, name, creditLimit, invoiceClosingDay));
    }

    @Test
    @DisplayName("Atualizar cliente - validação lança exceção")
    void testUpdateClientValidacaoLancandoExcecao() {
        Integer code = 1;
        String name = "Cliente Teste";
        String creditLimit = "2000.0";
        String invoiceClosingDay = "20";
        Client client = new Client(code, name, Double.parseDouble(creditLimit), Integer.parseInt(invoiceClosingDay));

        doThrow(InvalidClientDataException.class).when(updateClientValidator).validate(client);

        assertThrows(InvalidClientDataException.class, () -> clientController.updateClient(code, name, creditLimit, invoiceClosingDay));
    }

    @Test
    @DisplayName("Obter todos os clientes")
    void testGetAllClients() {
        List<Client> clients = Arrays.asList(new Client(), new Client());
        when(clientService.getAllClients()).thenReturn(clients);

        List<Client> result = clientController.getAllClients();

        assertEquals(clients.size(), result.size());
    }

    @Test
    @DisplayName("Obter cliente por código")
    void testGetClientByCode() {
        Client client = new Client();
        when(clientService.getClientByCode(1)).thenReturn(client);

        Client result = clientController.getClientByCode(1);

        assertEquals(client, result);
    }

    @Test
    @DisplayName("Deletar cliente - sucesso")
    void testDeleteClientSucesso() {
        int code = 1;

        clientController.deleteClient(code);

        verify(clientService).deleteClient(code);
    }

    @Test
    @DisplayName("Deletar cliente - código inválido")
    void testDeleteClientCodigoInvalido() {
        int code = -1;

        assertThrows(InvalidClientDataException.class, () -> clientController.deleteClient(code));
    }
}
