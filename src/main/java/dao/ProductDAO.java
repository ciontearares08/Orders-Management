package dao;

import java.util.logging.Logger;
import java.util.logging.Level;
import connection.ConnectionFactory;
import model.Product;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class ProductDAO {
    private static final Logger LOGGER = Logger.getLogger(ProductDAO.class.getName());

    public static Product findById(int id) {
        Product product = null;
        String FIND_BY_ID_SQL = "SELECT * FROM Product WHERE ProductID = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    product = extractProduct(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error finding product by id: " + e.getMessage());
        }
        return product;
    }

    public static int insert(Product product) {
        int insertedId = -1;
        String INSERT_SQL = "INSERT INTO Product (ProductID, ProductName, Description, Price, Stock) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, product.getProductId());
            statement.setString(2, product.getProductName());
            statement.setString(3, product.getDescription());
            statement.setDouble(4, product.getPrice());
            statement.setInt(5, product.getStock());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    insertedId = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error inserting product: " + e.getMessage());
        }
        return insertedId;
    }

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String FIND_ALL_SQL = "SELECT * FROM Product";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                products.add(extractProduct(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error finding all products: " + e.getMessage());
        }
        return products;
    }

    public static void update(Product product) {
        String UPDATE_SQL = "UPDATE Product SET ProductName = ?, Description = ?, Price = ?, Stock = ? WHERE ProductID = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, product.getProductName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getStock());
            statement.setInt(5, product.getProductId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error updating product: " + e.getMessage());
        }
    }

    public static void delete(int productId) {
        String DELETE_SQL = "DELETE FROM Product WHERE ProductID = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error deleting product: " + e.getMessage());
        }
    }

    public Product findByName(String productName) {
        Product product = null;
        String FIND_BY_NAME_SQL = "SELECT * FROM Product WHERE ProductName = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME_SQL)) {
            statement.setString(1, productName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    product = extractProduct(resultSet);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error finding product by name: " + e.getMessage());
        }
        return product;
    }

    private static Product extractProduct(ResultSet resultSet) throws SQLException {
        int productId = resultSet.getInt("ProductID");
        String productName = resultSet.getString("ProductName");
        String description = resultSet.getString("Description");
        double price = resultSet.getDouble("Price");
        int stock = resultSet.getInt("Stock");
        return new Product(productId, productName, description, price, stock);
    }
}