package com.vsoftware.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vsoftware.config.DatabaseConnection;
import com.vsoftware.dao.ClientDAO;
import com.vsoftware.dao.SaleDAO;
import com.vsoftware.dao.SaleItemDAO;
import com.vsoftware.domain.Client;
import com.vsoftware.domain.Product;
import com.vsoftware.domain.Sale;
import com.vsoftware.domain.SaleFilter;
import com.vsoftware.domain.SaleItem;
import com.vsoftware.exception.DatabaseException;

public class SaleDAOImpl implements SaleDAO {
	
	private String sql;	
	private SaleItemDAO saleItemDAO = new SaleItemDAOImpl();
	private ClientDAO clientDAO = new ClientDAOImpl();
	
	@Override
	public void create(Sale sale) {
		sql = "INSERT INTO Sales (client_code, sale_date, total_value) VALUES (?, ?, ?)";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            statement.setInt(1, sale.getClient().getCode());
            statement.setDate(2, new Date(sale.getSaleDate().getTime()));
            statement.setDouble(3, sale.getTotalValue());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    sale.setCode(generatedKeys.getInt(1));
                }
            }

            for (SaleItem item : sale.getSaleItems().values()) {
                item.setSale(sale);
                saleItemDAO.create(item);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao criar Venda: " + e.getMessage(), e);
        }
	}

	@Override
	public Sale getById(int code) {
		sql = "SELECT * FROM Sales WHERE code = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            statement.setInt(1, code);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Sale sale = new Sale();
                    sale.setCode(resultSet.getInt("code"));
                    sale.setSaleDate(resultSet.getDate("sale_date"));
                    sale.setTotalValue(resultSet.getDouble("total_value"));

                    
                    Client client = clientDAO.getClientByCode(resultSet.getInt("client_code"));
                    client.setCode(resultSet.getInt("client_code"));
                    sale.setClient(client);

                    sale.setSaleItems(saleItemDAO.findBySaleCode(sale.getCode()));

                    return sale;
                }
            }
        } catch (SQLException e) {
        	throw new DatabaseException("Erro ao buscar Venda pelo codigo: " + e.getMessage(), e);
        }
        return null;
	}

	@Override
	public List<Sale> getAll() {
		List<Sale> sales = new ArrayList<>();
        sql = "SELECT * FROM Sales";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Sale sale = new Sale();
                    sale.setCode(resultSet.getInt("code"));
                    sale.setSaleDate(resultSet.getDate("sale_date"));
                    sale.setTotalValue(resultSet.getDouble("total_value"));

                    Client client = clientDAO.getClientByCode(resultSet.getInt("client_code"));
                    client.setCode(resultSet.getInt("client_code"));
                    sale.setClient(client);

                    sale.setSaleItems(saleItemDAO.findBySaleCode(sale.getCode()));

                    sales.add(sale);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar todas as Vendas: " + e.getMessage(), e);
        }
        return sales;
	}

	@Override
	public void update(Sale sale) {
		sql = "UPDATE Sales SET client_code = ?, sale_date = ?, total_value = ? WHERE code = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            statement.setInt(1, sale.getClient().getCode());
            statement.setDate(2, new Date(sale.getSaleDate().getTime()));
            statement.setDouble(3, sale.getTotalValue());
            statement.setInt(4, sale.getCode());
            statement.executeUpdate();

            saleItemDAO.deleteBySaleCode(sale.getCode()); 
            for (SaleItem item : sale.getSaleItems().values()) {
                item.setSale(sale); 
                saleItemDAO.create(item);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao atualizar Venda: " + e.getMessage(), e);
        }
	}

	@Override
	public void delete(int code) {
		saleItemDAO.deleteBySaleCode(code);

        sql = "DELETE FROM Sales WHERE code = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            statement.setInt(1, code);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao remover Venda: " + e.getMessage(), e);
        }
	}

	@Override
	public Map<Client, Double> getSalesGroupedByClient(LocalDate start, LocalDate end) {
		Map<Client, Double> salesByClient = new HashMap<>();
        sql = "SELECT c.code, c.name, SUM(s.total_value) AS total FROM Sales s " +
              "JOIN Clients c ON s.client_code = c.code " +
              "WHERE s.sale_date BETWEEN ? AND ? " +
              "GROUP BY c.code, c.name";

        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(start));
            statement.setDate(2, Date.valueOf(end));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Client client = new Client();
                    client.setCode(resultSet.getInt("code"));
                    client.setName(resultSet.getString("name"));
                    salesByClient.put(client, resultSet.getDouble("total"));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar vendas agrupadas por cliente: " + e.getMessage(), e);
        }
        return salesByClient;
	}

	@Override
	public Map<Product, Double> getSalesGroupedByProduct(LocalDate start, LocalDate end) {
		Map<Product, Double> salesByProduct = new HashMap<>();
        sql = "SELECT p.code, p.description, SUM(si.quantity * p.price) AS total " +
              "FROM Sales s " +
              "JOIN SaleItems si ON s.code = si.sale_code " +
              "JOIN Products p ON si.product_code = p.code " +
              "WHERE s.sale_date BETWEEN ? AND ? " +
              "GROUP BY p.code, p.description";

        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(start));
            statement.setDate(2, Date.valueOf(end));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setCode(resultSet.getInt("code"));
                    product.setDescription(resultSet.getString("description"));
                    salesByProduct.put(product, resultSet.getDouble("total"));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar vendas agrupadas por produto: " + e.getMessage(), e);
        }
        return salesByProduct;
	}

	@Override
	public List<Sale> findByFilter(SaleFilter filter) {
		List<Sale> sales = new ArrayList<>();
	    StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM Sales s ");

	    List<Object> parameters = new ArrayList<>();

	    if (filter.getStartDate() != null) {
	        sqlBuilder.append("WHERE s.sale_date >= ? ");
	        parameters.add(Date.valueOf(filter.getStartDate()));
	    }
	    if (filter.getEndDate() != null) {
	        sqlBuilder.append(filter.getStartDate() != null ? "AND " : "WHERE ");
	        sqlBuilder.append("s.sale_date <= ? ");
	        parameters.add(Date.valueOf(filter.getEndDate()));
	    }
	    if (filter.getClientCode() != null) {
	        sqlBuilder.append(filter.getStartDate() != null || filter.getEndDate() != null ? "AND " : "WHERE ");
	        sqlBuilder.append("s.client_code = ? ");
	        parameters.add(filter.getClientCode());
	    }
	    if (filter.getProductCode() != null) {
	        sqlBuilder.append((filter.getStartDate() != null || filter.getEndDate() != null || filter.getClientCode() != null) ? "AND " : "WHERE ");
	        sqlBuilder.append("EXISTS (SELECT 1 FROM SaleItems si WHERE si.sale_code = s.code AND si.product_code = ?)");
	        parameters.add(filter.getProductCode());
	    }

	    try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(sqlBuilder.toString())) {

	        for (int i = 0; i < parameters.size(); i++) {
	            statement.setObject(i + 1, parameters.get(i));
	        }

	        try (ResultSet resultSet = statement.executeQuery()) {
	            while (resultSet.next()) {
	                sales.add(mapResultSetToSale(resultSet));
	            }
	        }
	    } catch (SQLException e) {
	        throw new DatabaseException("Erro ao buscar vendas com filtro: " + e.getMessage(), e);
	    }
	    return sales;
	}
	
	private Sale mapResultSetToSale(ResultSet resultSet) throws SQLException {
        Sale sale = new Sale();
        sale.setCode(resultSet.getInt("code"));
        LocalDate localDate = resultSet.getDate("sale_date").toLocalDate();
        sale.setSaleDate(Date.valueOf(localDate));
        sale.setTotalValue(resultSet.getDouble("total_value"));

        Client client = clientDAO.getClientByCode(resultSet.getInt("client_code"));
        sale.setClient(client);

        sale.setSaleItems(saleItemDAO.findBySaleCode(sale.getCode()));

        return sale;
    }

}
