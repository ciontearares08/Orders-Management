package dao;
import java.sql.Statement;
import connection.ConnectionFactory;
import model.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public static int insert(Order order) {
        Connection connection = ConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Orders (OrderID, ClientID, ProductID, OrderDate, Quantity, TotalAmount) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, order.getOrderId());
            preparedStatement.setInt(2, order.getClientId());
            preparedStatement.setInt(3, order.getProductId());
            preparedStatement.setTimestamp(4, new Timestamp(order.getOrderDate().getTime()));
            preparedStatement.setInt(5, order.getQuantity());
            preparedStatement.setDouble(6, order.getTotalAmount());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void update(Order order) {
        Connection connection = ConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Orders SET ClientID = ?, ProductID = ?, OrderDate = ?, Quantity = ?, TotalAmount = ? WHERE OrderID = ?");
            preparedStatement.setInt(1, order.getClientId());
            preparedStatement.setInt(2, order.getProductId());
            preparedStatement.setTimestamp(3, order.getOrderDate());
            preparedStatement.setInt(4, order.getQuantity());
            preparedStatement.setDouble(5, order.getTotalAmount());
            preparedStatement.setInt(6, order.getOrderId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(int orderId) {
        Connection connection = ConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Orders WHERE OrderID = ?");
            preparedStatement.setInt(1, orderId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Order> findAll() {
        Connection connection = ConnectionFactory.getConnection();
        List<Order> orders = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Orders");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order(
                        resultSet.getInt("OrderID"),
                        resultSet.getInt("ClientID"),
                        resultSet.getInt("ProductID"),
                        resultSet.getTimestamp("OrderDate"),
                        resultSet.getInt("Quantity"),
                        resultSet.getDouble("TotalAmount")
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public static Order findById(int orderId) {
        Connection connection = ConnectionFactory.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Orders WHERE OrderID = ?");
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Order(
                        resultSet.getInt("OrderID"),
                        resultSet.getInt("ClientID"),
                        resultSet.getInt("ProductID"),
                        resultSet.getTimestamp("OrderDate"),
                        resultSet.getInt("Quantity"),
                        resultSet.getDouble("TotalAmount")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



}