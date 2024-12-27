package model;

import java.sql.Timestamp;

public class Order {
    private int orderId;
    private int clientId;
    private int productId;
    private Timestamp orderDate;
    private int quantity;
    private double totalAmount;

    public Order(int orderId, int clientId, int productId, Timestamp orderDate, int quantity, double totalAmount) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.productId = productId;
        this.orderDate = orderDate;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    // getters and setters

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}