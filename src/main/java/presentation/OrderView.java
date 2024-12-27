package presentation;

import bll.ProductBLL;
import bll.StudentBLL;
import model.Product;
import model.Student;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class OrderView extends JFrame {
    private JComboBox<String> productComboBox;
    private JComboBox<String> studentComboBox;
    private JTextField quantityField;
    private JButton orderButton;
    private JButton backButton; // new back button
    private ProductBLL productBLL; // Declare
    private StudentBLL studentBLL;

    public JComboBox<String> getProductComboBox() {
        return productComboBox;
    }

    public JComboBox<String> getStudentComboBox() {
        return studentComboBox;
    }

    public JTextField getQuantityField() {
        return quantityField;
    }

    public JButton getOrderButton() {
        return orderButton;
    }

    public JButton getBackButton() { // getter for the back button
        return backButton;
    }

    public OrderView() {
        setTitle("Order Operations");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        productBLL = new ProductBLL(); // Initialize
        studentBLL = new StudentBLL(); // Initialize O

        productComboBox = new JComboBox<>();
        studentComboBox = new JComboBox<>();
        quantityField = new JTextField(20);
        orderButton = new JButton("Create Order");
        backButton = new JButton("Back"); // initialize the back button

        List<Product> products = productBLL.findAllProducts(); // Modify this line
        for (Product product : products) {
            productComboBox.addItem(product.getProductName());
        }

        List<Student> students = studentBLL.findAllStudents(); // Modify this line
        for (Student student : students) {
            studentComboBox.addItem(student.getName());
        }

        // add an action listener to the back button to dispose the current window when clicked
        backButton.addActionListener(e -> this.dispose());

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Select Product:"));
        panel.add(productComboBox);
        panel.add(new JLabel("Select Student:"));
        panel.add(studentComboBox);
        panel.add(new JLabel("Enter Quantity:"));
        panel.add(quantityField);
        panel.add(orderButton);
        panel.add(backButton); // add the back button to the panel

        add(panel);
    }
}