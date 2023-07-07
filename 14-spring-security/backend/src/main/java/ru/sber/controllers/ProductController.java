package ru.sber.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.Product;
import ru.sber.services.ProductService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Добавляет новый товар
     *
     * @param product Добавляемый товар
     * @return Возвращает статус добавления товара
     */
    @PostMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product) {
        long productId = productService.addNewProduct(product);
        log.info("Добавление товара {}", product);
        return ResponseEntity.created(URI.create("products/" + productId)).build();
    }

    /**
     * Выдает все товары по фильтру
     *
     * @param name Название товара (фильтр)
     * @return Возвращает список найденных товаров
     */
    @GetMapping
    public List<Product> getProducts(@RequestParam(required = false) String name) {

        if (name == null) {
            name = "";
            log.info("Вывод всех товаров");
        } else {
            log.info("Поиск товаров по имени {}", name);
        }

        return productService.findAllByName(name);
    }

    /**
     * Получение товара по id
     *
     * @param id Идентификатор для поиска
     * @return Возвращает найденный товар
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProducts(@PathVariable long id) {
        log.info("Получение товара по id");
        Optional<Product> product = productService.findById(id);

        if (product.isPresent()) {
            return ResponseEntity.ok().body(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Обновление информации о товаре
     *
     * @param product Информация об обновленном товаре
     * @return Возвращает обновленный товар
     */
    @PutMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody Product product) {
        productService.update(product);
        log.info("Обновление информации о товаре");
        return ResponseEntity.ok().body(product);
    }

    /**
     * Удаление товара по id
     *
     * @param id Идентификатор товара
     * @return Возвращает статус удаления товара
     */
    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        log.info("Удаление продукта по id");
        boolean isDeleted = productService.deleteById(id);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
