package ru.sber.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sber.entities.Product;
import ru.sber.repositories.ProductCartRepository;
import ru.sber.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductCartRepository productCartRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductCartRepository productCartRepository) {
        this.productRepository = productRepository;
        this.productCartRepository = productCartRepository;
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
    @Transactional
    public boolean deleteProductById(long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            productCartRepository.deleteByProduct(product);
            productRepository.deleteById(productId);

            return true;
        }

        return false;
    }

    @Override
    public List<Product> findProductByName(String productName) {
        return productRepository.findAll();
    }
}
