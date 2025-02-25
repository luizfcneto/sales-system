package com.vsoftware.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.vsoftware.config.DatabaseConnection;
import com.vsoftware.dao.ProductDAO;
import com.vsoftware.domain.Product;
import com.vsoftware.exception.DatabaseException;

public class ProductDAOImpl implements ProductDAO{
	
	private String sql;
	
	@Override
	public void create(Product product) {
		sql = "INSERT INTO Products (description, price) VALUES (?, ?)";

        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            statement.setString(1, product.getDescription());
            statement.setDouble(2, product.getPrice());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Erro ao criar produto: " + e.getMessage(), e);
        }		
	}

	@Override
	public void update(Product product) {
		sql = "UPDATE Products SET description = ?, price = ? WHERE code = ?";

        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            statement.setString(1, product.getDescription());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getCode());
            statement.executeUpdate();
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao atualizar produto: " + e.getMessage(), e);
        }		
	}

	@Override
	public List<Product> getAll() {
		sql = "SELECT * FROM Products";
        List<Product> products = new ArrayList<>();

        try (Statement statement = DatabaseConnection.getInstance().getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                products.add(mapResultSetToProduct(resultSet));
            }
            return products;
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar todos os produtos: " + e.getMessage(), e);
        }
	}

	@Override
	public Product getProductByCode(int code) {
		sql = "SELECT * FROM Products WHERE code = ?";

        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            statement.setInt(1, code);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
            	return null;
            }
            return mapResultSetToProduct(resultSet);
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar produto por código: " + e.getMessage(), e);
        }
	}

	@Override
	public void delete(int code) {
		sql = "DELETE FROM Products WHERE code = ?";

        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            statement.setInt(1, code);
            statement.executeUpdate();
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao deletar produto: " + e.getMessage(), e);
        }
	}
	
	private Product mapResultSetToProduct(ResultSet resultSet) throws SQLException {
        return new Product(
            resultSet.getInt("code"),
            resultSet.getString("description"),
            resultSet.getDouble("price")
        );
    }

}
