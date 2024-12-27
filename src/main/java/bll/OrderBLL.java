package bll;

import dao.OrderDAO;
import model.Order;

import java.util.List;

public class OrderBLL {

    private static OrderDAO orderDAO;

    public OrderBLL() {
        this.orderDAO = new OrderDAO();
    }

    public Order findOrderById(int id) {
        return orderDAO.findById(id);
    }

    public static void insertOrder(Order order) {
        orderDAO.insert(order);
    }

    public void updateOrder(Order order) {
        orderDAO.update(order);
    }

    public void deleteOrder(int id) {
        orderDAO.delete(id);
    }

    public static List<Order> findAllOrders() {
        return orderDAO.findAll();
    }
}