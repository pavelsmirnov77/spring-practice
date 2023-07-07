package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.Cart;
import ru.sber.entities.Product;
import ru.sber.services.CartService;
import ru.sber.services.ProductService;

import java.net.URI;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("cart")
public class CartController {

    CartService cartService;
    ProductService productService;

    @Autowired
    public CartController(CartService cartService, ProductService productService) {
        this.cartService = cartService;
        this.productService = productService;
    }

    /**
     * Добавляет товар в корзину
     *
     * @param idClient  id клиента
     * @param idProduct id товара
     * @return корзину с добавленным товаром
     */
    @PostMapping("/{idClient}/products/{idProduct}")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Cart> addProductToCart(@PathVariable long idClient, @PathVariable Long idProduct, @RequestBody Product product) {

        log.info("Добавление в корзину товара с id: {}", idProduct);

        boolean recordInserted = cartService.addToCart(idClient, idProduct);

        if (recordInserted) {
            return ResponseEntity.created(URI.create("cart/" + idClient + "/product/" + idProduct)).build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    /**
     * Изменяет количество товара в корзине
     *
     * @param idCart    id корзины
     * @param idProduct id товара
     * @param product   товар, у которого изменяется количество
     * @return Возвращает корзину с внесенными изменениями
     */
    @PutMapping("/{idCart}/product/{idProduct}")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Cart> updateProductAmountInCart(@PathVariable long idCart, @PathVariable long idProduct, @RequestBody Product product) {

        log.info("Изменяется количество товара в корзине");

        boolean recordUpdated = cartService.updateProductAmount(idCart, idProduct, (int) product.getQuantity());

        if (recordUpdated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Удаляет товар из корзины
     *
     * @param idCart    Уникальный идентификатор корзины
     * @param idProduct Уникальный идентификатор продукта
     * @return Возвращает корзину с внесенными изменениями
     */
    @DeleteMapping("/{idCart}/product/{idProduct}")
    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable long idCart, @PathVariable long idProduct) {

        log.info("Удаление из корзины товара с id: {}", idProduct);

        boolean isDeleted = cartService.deleteProduct(idCart, idProduct);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
