package ru.sber.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.entities.Product;
import ru.sber.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements ProductServiceImpl {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public long createProduct(Product product) {
        Product createdProduct = productRepository.save(product);

        return createdProduct.getId();
    }

    @Override
    public boolean changeProduct(Product product) {
        productRepository.save(product);

        return true;
    }

    @Override
    public boolean deleteProductById(long productId) {
        productRepository.deleteById(productId);

        return true;
    }

    @Override
    public List<Product> findProductByName(String productName) {
        return productRepository.findByName(productName);
    }

    @Override
    public Optional<Product> getProductById(long productId) {
        return productRepository.findById(productId);
    }
}
