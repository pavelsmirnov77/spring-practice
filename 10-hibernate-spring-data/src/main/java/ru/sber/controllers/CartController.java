package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.Cart;
import ru.sber.entities.Payment;
import ru.sber.entities.Product;
import ru.sber.services.CartService;
import ru.sber.services.CartServiceImpl;
import ru.sber.services.ProductService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("carts")
public class CartController {
    private final CartServiceImpl cartServiceImpl;

    @Autowired
    public CartController(CartServiceImpl cartServiceImpl) {
        this.cartServiceImpl = cartServiceImpl;
    }

    @PostMapping("/{cartId}/products/{productId}")
    public void addProductToCart(@PathVariable long cartId, @PathVariable long productId) {
        log.info("Товар с id {} добавляется в корзину с id {}", productId, cartId);
        cartServiceImpl.addProductById(cartId, productId);
    }

    @PutMapping("/{cartId}/products/{productId}")
    public ResponseEntity<?> changeProductQuantity(@PathVariable long cartId, @PathVariable long productId, @RequestBody Product product) {
        long quantity = product.getQuantity();
        log.info("Изменяется количество товара с id {} в корзине с id {} на значение {}", productId, cartId, quantity);

        boolean quantityChanged = cartServiceImpl.changeQuantity(cartId, productId, quantity);
        HttpStatus status = quantityChanged ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return ResponseEntity.status(status).body(quantityChanged);
    }

    @DeleteMapping("/{cartId}/products/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable long cartId, @PathVariable long productId) {
        boolean deleted = cartServiceImpl.deleteProductFromCart(cartId, productId);
        if (deleted) {
            return ResponseEntity.ok("Товар удален из корзины");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/payment/{cartId}")
    public Optional<Payment> processPayment(@PathVariable long cartId) {
        log.info("Проходит оплата корзины с id {}", cartId);
        return cartServiceImpl.payment(cartId);
    }

    @GetMapping("/{cartId}")
    public List<Product> getCartById(@PathVariable long cartId) {
        log.info("Корзина с id {} получена", cartId);
        return cartServiceImpl.getCartById(cartId);
    }
}
