package com.vsoftware.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.vsoftware.config.DatabaseConnection;
import com.vsoftware.dao.SaleItemDAO;
import com.vsoftware.domain.Product;
import com.vsoftware.domain.Sale;
import com.vsoftware.domain.SaleItem;
import com.vsoftware.exception.DatabaseException;

public class SaleItemDAOImpl implements SaleItemDAO {
	
	private String sql;
	
	@Override
	public void create(SaleItem saleItem) {
		sql = "INSERT INTO ItensSale (sale_code, product_code, quantity) VALUES (?, ?, ?)";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            statement.setInt(1, saleItem.getSale().getCode());
            statement.setInt(2, saleItem.getProduct().getCode());
            statement.setInt(3, saleItem.getQuantity());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao cadastrar Item de Venda: " + e.getMessage(), e);
        }
	}

	@Override
	public SaleItem getByCode(int saleCode, int productCode) {
		sql = "SELECT its.*, p.price, p.description FROM ItensSale its " +
	            "INNER JOIN Products p ON its.product_code = p.code " +
	            "WHERE its.sale_code = ? AND its.product_code = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            statement.setInt(1, saleCode);
            statement.setInt(2, productCode);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    SaleItem item = new SaleItem();
                    Sale sale = new Sale();
                    sale.setCode(resultSet.getInt("sale_code"));
                    item.setSale(sale);

                    Product product = new Product();
                    product.setCode(resultSet.getInt("product_code"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setDescription(resultSet.getString("description"));
                    item.setProduct(product);

                    item.setQuantity(resultSet.getInt("quantity"));
                    return item;
                }
            }
            
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar Item de Venda pelo codigo da venda e codigo do item: " + e.getMessage(), e);
        }
        return null;
	}

	@Override
	public Map<Integer, SaleItem> findBySaleCode(int saleCode) {
		Map<Integer, SaleItem> saleItems = new LinkedHashMap<>();
        sql = "SELECT its.*, p.price FROM ItensSale its " +
                "INNER JOIN Products p ON its.product_code = p.code " +
                "WHERE its.sale_code = ?";
        
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            statement.setInt(1, saleCode);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    SaleItem item = new SaleItem();

                    Sale sale = new Sale();
                    sale.setCode(resultSet.getInt("sale_code"));
                    item.setSale(sale);

                    Product product = new Product();
                    product.setCode(resultSet.getInt("product_code"));
                    product.setPrice(resultSet.getDouble("price"));
                    item.setProduct(product);

                    item.setQuantity(resultSet.getInt("quantity"));
                    saleItems.put(item.getProduct().getCode(), item);
                }
            }
        
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao buscar Item de Venda pelo codigo da venda: " + e.getMessage(), e);
        }
        return saleItems;
	}

	@Override
	public void update(SaleItem saleItem) {
		sql = "UPDATE ItensSale SET quantity = ? WHERE sale_code = ? AND product_code = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            statement.setInt(1, saleItem.getQuantity());
            statement.setInt(2, saleItem.getSale().getCode());
            statement.setInt(3, saleItem.getProduct().getCode());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao atualizar Item de Venda: " + e.getMessage(), e);
        }	
	}

	@Override
	public void delete(int saleCode, int productCode) {
		sql = "DELETE FROM ItensSale WHERE sale_code = ? AND product_code = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            statement.setInt(1, saleCode);
            statement.setInt(2, productCode);
            statement.executeUpdate();
        } catch (SQLException e) {
        	throw new DatabaseException("Erro ao remover Item de Venda pelo codigo da venda e codigo do produto: " + e.getMessage(), e);
        }		
	}

	@Override
	public void deleteBySaleCode(int saleCode) {
		sql = "DELETE FROM ItensSale WHERE sale_code = ?";
        try (PreparedStatement statement = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            statement.setInt(1, saleCode);
            statement.executeUpdate();
        } catch (SQLException e) {
        	throw new DatabaseException("Erro ao remover Item de Venda por codigo de venda: " + e.getMessage(), e);
        }		
	}

}
