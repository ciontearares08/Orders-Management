package presentation;

import bll.OrderBLL;
import bll.ProductBLL;
import bll.StudentBLL;
import model.Order;
import model.Product;
import model.Student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderController {
    private OrderView orderView;
    private ProductBLL productBLL; // Declare productBLL
    private StudentBLL studentBLL;

    public OrderController(OrderView orderView) {
        this.orderView = orderView;
        this.productBLL = new ProductBLL(); // Initialize productBLL
        this.studentBLL = new StudentBLL();

        orderView.getOrderButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Disable the order button
                orderView.getOrderButton().setEnabled(false);

                String selectedProductName = (String) orderView.getProductComboBox().getSelectedItem();
                Product selectedProduct = productBLL.findProductByName(selectedProductName); // Modify this line

                String selectedStudentName = (String) orderView.getStudentComboBox().getSelectedItem();
                Student selectedStudent = studentBLL.findByName(selectedStudentName); // Modify this line

                int quantity = Integer.parseInt(orderView.getQuantityField().getText());

                if (selectedProduct.getStock() < quantity) {
                    JOptionPane.showMessageDialog(null, "Not enough stock for this product.");
                } else {
                    int orderId = (int) (System.currentTimeMillis() / 1000);
                    Order order = new Order(orderId, selectedStudent.getId(), selectedProduct.getProductId(), new java.sql.Timestamp(System.currentTimeMillis()), quantity, selectedProduct.getPrice() * quantity);
                    OrderBLL.insertOrder(order);
                    selectedProduct.setStock(selectedProduct.getStock() - quantity);
                    ProductBLL.updateProduct(selectedProduct);
                    JOptionPane.showMessageDialog(null, "Order created successfully.");
                }

                // Enable the order button
                orderView.getOrderButton().setEnabled(true);
            }
        });
    }
}