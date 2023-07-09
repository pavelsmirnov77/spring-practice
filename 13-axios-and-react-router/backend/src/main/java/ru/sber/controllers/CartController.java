package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
     * @param clientId  id клиента
     * @param productId id товара
     * @return корзину с добавленным товаром
     */
    @PostMapping("/{clientId}/product/{productId}")
    public ResponseEntity<Cart> addProductToCart(@PathVariable long clientId, @PathVariable Long productId, @RequestBody Product product) {

        log.info("Добавление в корзину товара с id: {}", productId);

        boolean recordInserted = cartService.addToCart(clientId, productId);

        if (recordInserted) {
            return ResponseEntity.created(URI.create("cart/" + clientId + "/product/" + productId)).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Изменяет количество товара в корзине
     *
     * @param cartId    id корзины
     * @param productId id товара
     * @param product   товар, у которого изменяется количество
     * @return Возвращает корзину с внесенными изменениями
     */
    @PutMapping("/{cartId}/product/{productId}")
    public ResponseEntity<Cart> updateProductAmountInCart(@PathVariable long cartId, @PathVariable long productId, @RequestBody Product product) {

        log.info("Изменяется количество товара в корзине");

        boolean recordUpdated = cartService.updateProductAmount(cartId, productId, (int) product.getQuantity());

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
