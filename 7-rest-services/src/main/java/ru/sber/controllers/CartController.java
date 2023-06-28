package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.Cart;
import ru.sber.repositories.CartRepository;

@Slf4j
@RestController
@RequestMapping("carts")
public class CartController {
    private final CartRepository cartRepository;

    @Autowired
    public CartController(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @PostMapping("/{cartId}/products/{productId}")
    public boolean addProductToCart(@PathVariable long cartId, @PathVariable long productId) {
        return cartRepository.addProductById(cartId, productId);
    }

    @PutMapping("/{cartId}/products/{productId}")
    public boolean changeProductQuantity(@PathVariable long cartId, @PathVariable long productId, @RequestParam long quantity) {
        return cartRepository.changeQuantity(cartId, productId, quantity);
    }

    @DeleteMapping("/{cartId}/products/{productId}")
    public ResponseEntity<?> deleteProductFromCart(@PathVariable long cartId, @PathVariable long productId) {
        boolean isDeleted = cartRepository.deleteProductFromCart(cartId, productId);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{cartId}/payment")
    public boolean processPayment(@PathVariable long cartId) {
        return cartRepository.payment(cartId);
    }

    @GetMapping("/{cartId}")
    public Cart getCartById(@PathVariable long cartId) {
        return cartRepository.getCartById(cartId);
    }
}
