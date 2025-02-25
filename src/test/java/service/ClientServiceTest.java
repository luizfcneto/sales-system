package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.vsoftware.dao.ClientDAO;
import com.vsoftware.domain.Client;
import com.vsoftware.exception.DatabaseException;
import com.vsoftware.service.impl.ClientServiceImpl;

public class ClientServiceTest {
	@Mock
    private ClientDAO clientDAO;

    private ClientServiceImpl clientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        clientService = new ClientServiceImpl(clientDAO);
    }

    @Test
    @DisplayName("Teste criar cliente - sucesso")
    void testCreateClientSucesso() {
        Client client = new Client("Cliente Teste", 1000.0, 15);
        clientService.createClient(client);
        verify(clientDAO, times(1)).create(client);
    }

    @Test
    @DisplayName("Teste criar cliente - exceção DatabaseException")
    void testCreateClientDatabaseException() {
        Client client = new Client("Cliente Teste", 1000.0, 15);
        doThrow(DatabaseException.class).when(clientDAO).create(client);
        assertThrows(DatabaseException.class, () -> clientService.createClient(client));
    }

    @Test
    @DisplayName("Teste atualizar cliente - sucesso")
    void testUpdateClientSucesso() {
        Client client = new Client(1, "Cliente Teste", 1000.0, 15);
        clientService.updateClient(client);
        verify(clientDAO, times(1)).update(client);
    }

    @Test
    @DisplayName("Teste atualizar cliente - exceção DatabaseException")
    void testUpdateClientDatabaseException() {
        Client client = new Client(1, "Cliente Teste", 1000.0, 15);
        doThrow(DatabaseException.class).when(clientDAO).update(client);
        assertThrows(DatabaseException.class, () -> clientService.updateClient(client));
    }

    @Test
    @DisplayName("Teste obter todos os clientes - sucesso")
    void testGetAllClientsSucesso() {
        List<Client> clientes = Arrays.asList(
                new Client(1, "Cliente 1", 1000.0, 15),
                new Client(2, "Cliente 2", 2000.0, 20)
        );
        when(clientDAO.getAll()).thenReturn(clientes);

        List<Client> result = clientService.getAllClients();

        assertEquals(2, result.size());
        assertEquals("Cliente 1", result.get(0).getName());
        assertEquals("Cliente 2", result.get(1).getName());

    }

    @Test
    @DisplayName("Teste obter todos os clientes - exceção DatabaseException")
    void testGetAllClientsDatabaseException() {
        when(clientDAO.getAll()).thenThrow(DatabaseException.class);
        assertThrows(DatabaseException.class, () -> clientService.getAllClients());
    }

    @Test
    @DisplayName("Teste obter cliente por código - sucesso")
    void testGetClientByCodeSucesso() {
        Client client = new Client(1, "Cliente Teste", 1000.0, 15);
        when(clientDAO.getClientByCode(1)).thenReturn(client);
        Client result = clientService.getClientByCode(1);
        assertEquals("Cliente Teste", result.getName());
    }

    @Test
    @DisplayName("Teste obter cliente por código - exceção DatabaseException")
    void testGetClientByCodeDatabaseException() {
        when(clientDAO.getClientByCode(1)).thenThrow(DatabaseException.class);
        assertThrows(DatabaseException.class, () -> clientService.getClientByCode(1));
    }

    @Test
    @DisplayName("Teste deletar cliente - sucesso")
    void testDeleteClientSucesso() {
        clientService.deleteClient(1);
        verify(clientDAO, times(1)).delete(1);
    }

    @Test
    @DisplayName("Teste deletar cliente - exceção DatabaseException")
    void testDeleteClientDatabaseException() {
        doThrow(DatabaseException.class).when(clientDAO).delete(1);
        assertThrows(DatabaseException.class, () -> clientService.deleteClient(1));
    }
}
