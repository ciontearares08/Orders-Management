package presentation;

import bll.ProductBLL;
import model.Product;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductOperationsController {
    private final ProductOperationsView view;

    public ProductOperationsController(ProductOperationsView view) {
        this.view = view;
        view.getAddButton().addActionListener(new AddButtonListener());
        view.getEditButton().addActionListener(new EditButtonListener());
        view.getDeleteButton().addActionListener(new DeleteButtonListener());
        view.getListButton().addActionListener(new ListButtonListener());
    }

    private class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(view.getProductIdField().getText());
            String name = view.getProductNameField().getText();
            String description = view.getDescriptionField().getText();
            double price = Double.parseDouble(view.getPriceField().getText());
            int stock = Integer.parseInt(view.getStockField().getText());

            Product product = new Product(id, name, description, price, stock);
            ProductBLL.insertProduct(product);
            view.updateTable();
        }
    }

    private class EditButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(view.getProductIdField().getText());
            String name = view.getProductNameField().getText();
            String description = view.getDescriptionField().getText();
            double price = Double.parseDouble(view.getPriceField().getText());
            int stock = Integer.parseInt(view.getStockField().getText());

            Product product = new Product(id, name, description, price, stock);
            ProductBLL.updateProduct(product);
            view.updateTable();
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(view.getProductIdField().getText());
            ProductBLL.deleteProduct(id);
            view.updateTable();
        }
    }

    private class ListButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.showProductList();
        }
    }
}