package com.vsoftware.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.vsoftware.config.DatabaseConnection;
import com.vsoftware.dao.ClientDAO;
import com.vsoftware.domain.Client;
import com.vsoftware.exception.DatabaseException;

public class ClientDAOImpl implements ClientDAO {
	
	private String sql;

	@Override
	public void create(Client client) {
		sql = "INSERT INTO Clients (name, credit_limit, invoice_closing_day) VALUES (?, ?, ?)";

        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            statement.setString(1, client.getName());
            statement.setDouble(2, client.getCreditLimit());
            statement.setInt(3, client.getInvoiceClosingDay());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao tentar criar um cliente novo: " + e.getMessage());
        } 	
	}

	@Override
	public void update(Client client) {
		sql = "UPDATE Clients SET name = ?, credit_limit = ?, invoice_closing_day = ? WHERE code = ?";

        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            statement.setString(1, client.getName());
            statement.setDouble(2, client.getCreditLimit());
            statement.setInt(3, client.getInvoiceClosingDay());
            statement.setInt(4, client.getCode());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error ao tentar atualizar o cliente: " + e.getMessage());
        } 	
	}

	@Override
	public List<Client> getAll() {
		sql = "SELECT * FROM Clients";
        List<Client> clients = new ArrayList<>();

        try (Statement statement = DatabaseConnection.getInstance().getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Client client = mapResultSetToClient(resultSet);
                clients.add(client);
            }
            
            return clients;
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao tentar buscar todos os clientes: " + e.getMessage());
        } 
	}
	
	@Override
	public Client getClientByCode(int code) {
		sql = "SELECT * FROM Clients WHERE code = ?";
		
		try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)){
			statement.setInt(1, code);
			ResultSet resultSet = statement.executeQuery();
			if(!resultSet.next()) {
				return null;
			}
			return mapResultSetToClient(resultSet);
			
		}catch(SQLException e) {
			throw new DatabaseException("Erro ao tentar buscar um cliente específico: " + e.getMessage());
		} 
	}

	@Override
	public void delete(int code) {
		sql = "DELETE FROM Clients WHERE code = ?";

        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            statement.setInt(1, code);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao tentar deletar o cliente: " + e.getMessage());
        } 	
	}
    
    private Client mapResultSetToClient(ResultSet resultSet) throws SQLException {
        return new Client(
    		resultSet.getInt("code"), 
    		resultSet.getString("name"), 
    		resultSet.getDouble("credit_limit"), 
    		resultSet.getInt("invoice_closing_day")
		);
    }

	@Override
	public double getTotalSpentSince(Client client, LocalDate date) {
		sql = "SELECT COALESCE(SUM(s.total_value), 0) FROM sales s WHERE s.client_code = ? AND s.sale_date >= ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection(); 
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, client.getCode());
            statement.setDate(2, Date.valueOf(date));

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble(1);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao obter total gasto desde a data" + e.getMessage());
        }
        return 0.0;
	}

	

}
