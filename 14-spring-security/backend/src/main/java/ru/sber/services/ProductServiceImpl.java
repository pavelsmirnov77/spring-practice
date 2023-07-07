package ru.sber.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.entities.Product;
import ru.sber.exceptions.ProductsException;
import ru.sber.repositories.CartRepository;
import ru.sber.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для взаимодействия с товарами
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public long addNewProduct(Product product) {
        return productRepository.save(product).getId();
    }

    @Override
    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findAllByName(String name) {
        return productRepository.findAllByNameContainingIgnoreCase(name);
    }

    @Override
    public boolean update(Product product) {
        productRepository.save(product);
        return true;
    }

    @Override
    public boolean checkProductExistence(long productId) {
        return productRepository.existsById(productId);
    }

    @Override
    public boolean deleteById(long productId) {

        if (checkProductExistence(productId)) {
            if (cartRepository.countCartsByProduct_Id(productId) == 0) {
                productRepository.deleteById(productId);
                return true;
            }
            throw new ProductsException("Товар не найден");
        }

        return false;
    }


}
