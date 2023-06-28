package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.Product;
import ru.sber.exceptions.ProductNotFoundException;
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
        log.info("Добавление товара {}", product);

        return productRepository.createProduct(product);
    }

    @GetMapping
    public ResponseEntity<?> getProducts(@RequestParam String productName) {
        try {
            log.info("Поиск товара по названию: {}", productName);
            List<Product> product = productRepository.findProductByName(productName);
            return ResponseEntity.ok().body(product);
        } catch (ProductNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }


    @PutMapping
    public Product updateProduct(@RequestBody Product product) {
        log.info("Обновление продукта: {}", product);
        productRepository.changeProduct(product);

        return product;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        boolean isDeleted = productRepository.deleteProductById(id);

        if (isDeleted) {
            log.info("Удаление товара с id {} прошло успешно", id);
            return ResponseEntity.noContent().build();
        } else {
            log.info("Товар с id {} не найден", id);
            return ResponseEntity.notFound().build();
        }
    }
}
