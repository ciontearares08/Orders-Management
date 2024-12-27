package presentation;

import bll.ProductBLL;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ProductOperationsView extends JFrame {
    private JTextField productIdField;
    private JTextField productNameField;
    private JTextField descriptionField;
    private JTextField priceField;
    private JTextField stockField;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton listButton;
    private JButton backButton;
    private JTable productTable;

    private ProductBLL productBLL;

    public ProductOperationsView() {
        setTitle("Product Operations");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        productBLL = new ProductBLL();

        productIdField = new JTextField(20);
        productNameField = new JTextField(20);
        descriptionField = new JTextField(20);
        priceField = new JTextField(20);
        stockField = new JTextField(20);

        addButton = new JButton("Add Product");
        editButton = new JButton("Edit Product");
        deleteButton = new JButton("Delete Product");
        listButton = new JButton("List Products");
        backButton = new JButton("Back");
        productTable = new JTable();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Product ID:"));
        panel.add(productIdField);

        panel.add(new JLabel("Product Name:"));
        panel.add(productNameField);

        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);

        panel.add(new JLabel("Price:"));
        panel.add(priceField);

        panel.add(new JLabel("Stock:"));
        panel.add(stockField);

        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(listButton);
        panel.add(backButton);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        deleteButton.addActionListener(e -> {
            int id = Integer.parseInt(productIdField.getText());
            Product product = productBLL.findProductById(id);
            if (product != null) {
                productBLL.deleteProduct(product.getProductId());
                updateTable();
            }
        });

        addButton.addActionListener(e -> {
            int id = Integer.parseInt(productIdField.getText());
            String name = productNameField.getText();
            String description = descriptionField.getText();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());

            Product product = new Product(id, name, description, price, stock);
            productBLL.insertProduct(product);
            updateTable();
        });

        editButton.addActionListener(e -> {
            int id = Integer.parseInt(productIdField.getText());
            String name = productNameField.getText();
            String description = descriptionField.getText();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());

            Product product = new Product(id, name, description, price, stock);
            productBLL.updateProduct(product);
            updateTable();
        });

        listButton.addActionListener(e -> showProductList());

        backButton.addActionListener(e -> dispose());
    }

    void showProductList() {
        JFrame productListFrame = new JFrame("List of Products");
        productListFrame.setSize(800, 600);
        productListFrame.setLocationRelativeTo(null);

        JTable productTable = new JTable();
        productListFrame.add(new JScrollPane(productTable), BorderLayout.CENTER);

        List<Product> products = productBLL.findAllProducts();
        DefaultTableModel model = createTableModelFromList(products);

        productTable.setModel(model);
        productListFrame.setVisible(true);
    }

    public DefaultTableModel createTableModelFromList(List<?> list) {
        DefaultTableModel model = new DefaultTableModel();

        if (!list.isEmpty()) {
            Object firstObject = list.get(0);
            Field[] fields = firstObject.getClass().getDeclaredFields();

            for (Field field : fields) {
                model.addColumn(field.getName());
            }

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

    public JTextField getProductIdField() {
        return productIdField;
    }

    public void setProductIdField(JTextField productIdField) {
        this.productIdField = productIdField;
    }

    public JTextField getProductNameField() {
        return productNameField;
    }

    public void setProductNameField(JTextField productNameField) {
        this.productNameField = productNameField;
    }

    public JTextField getDescriptionField() {
        return descriptionField;
    }

    public void setDescriptionField(JTextField descriptionField) {
        this.descriptionField = descriptionField;
    }

    public JTextField getPriceField() {
        return priceField;
    }

    public void setPriceField(JTextField priceField) {
        this.priceField = priceField;
    }

    public JTextField getStockField() {
        return stockField;
    }

    public void setStockField(JTextField stockField) {
        this.stockField = stockField;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public void setAddButton(JButton addButton) {
        this.addButton = addButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public void setEditButton(JButton editButton) {
        this.editButton = editButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(JButton deleteButton) {
        this.deleteButton = deleteButton;
    }

    public JButton getListButton() {
        return listButton;
    }

    public void setListButton(JButton listButton) {
        this.listButton = listButton;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public void setBackButton(JButton backButton) {
        this.backButton = backButton;
    }

    public JTable getProductTable() {
        return productTable;
    }

    public void setProductTable(JTable productTable) {
        this.productTable = productTable;
    }

    public void updateTable() {
        List<Product> products = productBLL.findAllProducts();
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Price");
        model.addColumn("Quantity");

        for (Product product : products) {
            model.addRow(new Object[]{
                    product.getProductId(),
                    product.getProductName(),
                    product.getPrice(),
                    product.getStock()
            });
        }
        productTable.setModel(model);
    }
}