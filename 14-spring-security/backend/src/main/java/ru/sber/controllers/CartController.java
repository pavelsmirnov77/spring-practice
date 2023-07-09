package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.Cart;
import ru.sber.entities.Product;
import ru.sber.services.CartService;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * Добавляет товар в корзину
     *
     * @param clientId  id клиента
     * @param productId id товара
     * @return корзину с добавленным товаром
     */
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
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
     * @param productId id продукта
     * @param product   Товар, у которого изменяется количество
     * @return корзина с внесенными изменениями
     */
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/{cartId}/product/{productId}")
    public ResponseEntity<Cart> updateProductAmountInCart(@PathVariable long cartId, @PathVariable long productId, @RequestBody Product product) {

        log.info("Изменение количества товара в корзине");

        boolean recordUpdated = cartService.updateProductAmount(cartId, productId, product.getQuantity());

        if (recordUpdated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{cartId}")
    public ResponseEntity<?> getProducts(@PathVariable long cartId) {

        log.info("Получение корзины пользователя с id {}", cartId);

        List<Product> productsInCart = cartService.getListOfProductsInCart(cartId);

        return ResponseEntity.ok().body(productsInCart);


    }

    /**
     * Удаляет товар из корзины
     *
     * @param cartId    id корзины
     * @param productId id продукта
     * @return корзина с внесенными изменениями
     */
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping("/{cartId}/product/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable long cartId, @PathVariable long productId) {

        log.info("Удаление из корзины товара с id: {}", productId);

        boolean isDeleted = cartService.deleteProduct(cartId, productId);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
