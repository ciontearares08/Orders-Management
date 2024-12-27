package presentation;

import bll.OrderBLL;
import model.Order;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;

public class MainView extends JFrame {
    private JButton clientButton;
    private JButton productButton;
    private JButton placeOrderButton;
    private JButton ordersButton;

    public MainView() {
        setTitle("Main Operations");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        clientButton = new JButton("Client Operations");
        productButton = new JButton("Product Operations");
        placeOrderButton = new JButton("Place Order");
        ordersButton = new JButton("View Orders");

        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open ClientOperationsView
                ClientOperationsView clientView = new ClientOperationsView();
                clientView.setVisible(true);
            }
        });

        productButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open ProductView
                ProductOperationsView productView = new ProductOperationsView();
                productView.setVisible(true);
            }
        });

        placeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open OrderView
                OrderView orderView = new OrderView();
                new OrderController(orderView);
                orderView.setVisible(true);
            }
        });

        ordersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showOrdersList();
            }
        });

        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.add(clientButton);
        panel.add(productButton);
        panel.add(placeOrderButton);
        panel.add(ordersButton);

        add(panel);
    }

    private void showOrdersList() {
        JFrame ordersListFrame = new JFrame("List of Orders");
        ordersListFrame.setSize(800, 600);
        ordersListFrame.setLocationRelativeTo(null);

        JTable ordersTable = new JTable();
        ordersListFrame.add(new JScrollPane(ordersTable), BorderLayout.CENTER);

        List<Order> orders = OrderBLL.findAllOrders();
        DefaultTableModel model = createTableModelFromList(orders);

        ordersTable.setModel(model);
        ordersListFrame.setVisible(true);
    }

    public DefaultTableModel createTableModelFromList(List<?> list) {
        DefaultTableModel model = new DefaultTableModel();

        if (!list.isEmpty()) {
            Object firstObject = list.get(0);
            Field[] fields = firstObject.getClass().getDeclaredFields();

            // Create table columns
            for (Field field : fields) {
                model.addColumn(field.getName());
            }

            // Create table rows
            for (Object object : list) {
                Object[] row = new Object[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    try {
                        fields[i].setAccessible(true);
                        row[i] = fields[i].get(object);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                model.addRow(row);
            }
        }

        return model;
    }
}