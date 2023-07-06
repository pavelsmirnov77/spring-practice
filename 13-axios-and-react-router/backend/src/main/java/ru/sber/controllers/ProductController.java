package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.Product;
import ru.sber.services.ProductService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Контроллер для обработки запросов к товарам
 */
@Slf4j
@RestController
@RequestMapping("products")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product) throws URISyntaxException {
        log.info("Добавляется товар {}", product);
        long id = productService.createProduct(product);
        return ResponseEntity
                .created(new URI("http://localhost:8080/products/" + id))
                .build();
    }

    @GetMapping
    public List<Product> getProduct(@RequestParam(required = false) String productName) {
        log.info("Получение товаров");

        return productService.findProductByName(productName);
    }

    @PutMapping
    public Product updateProduct(@RequestBody Product product) {
        log.info("Обновляется товар: {}", product);
        productService.changeProduct(product);

        return product;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        boolean isDeleted = productService.deleteProductById(id);

        if (isDeleted) {
            log.info("Удаление товара с id {} прошло успешно", id);
            return ResponseEntity.noContent().build();
        } else {
            log.info("Товар с id {} не найден", id);
            return ResponseEntity.notFound().build();
        }
    }
}
