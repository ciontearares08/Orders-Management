package bll;

import dao.ProductDAO;
import model.Product;

import java.util.List;

public class ProductBLL {

    private static ProductDAO productDAO;

    public ProductBLL() {
        this.productDAO = new ProductDAO();
    }

    public Product findProductById(int id) {
        return productDAO.findById(id);
    }
    public Product findProductByName(String productName) {
        return productDAO.findByName(productName);
    }
    public static void insertProduct(Product product) {
        productDAO.insert(product);
    }

    public static void updateProduct(Product product) {
        productDAO.update(product);
    }

    public static void deleteProduct(int id) {
        productDAO.delete(id);
    }

    public List<Product> findAllProducts() {
        return productDAO.findAll();
    }
}