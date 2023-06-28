package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.Product;
import ru.sber.repositories.ProductRepository;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping
    public long addProduct(@RequestBody Product product) {
        log.info("Добавление продукта {}", product);

        return productRepository.createProduct(product);
    }

    @GetMapping
    public List<Product> getProducts(@RequestParam(required = false) String productName) {
        log.info("Поиск продуктов по имени {}", productName);

        return productRepository.findProductByName(productName);
    }

    @PutMapping
    public Product updateProduct(@RequestBody Product product) {
        productRepository.changeProduct(product);

        return product;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        boolean isDeleted = productRepository.deleteProductById(id);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
