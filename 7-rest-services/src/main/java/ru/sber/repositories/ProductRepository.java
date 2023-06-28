package ru.sber.repositories;

import ru.sber.entities.Product;

import java.util.List;

public interface ProductRepository {
    long createProduct(Product product);

    boolean changeProduct(Product product);

    boolean deleteProductById(long productId);

    List<Product> findProductByName(String productName);

    Product getProductById(long productId);

}
