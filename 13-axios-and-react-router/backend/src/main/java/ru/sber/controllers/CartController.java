package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.Payment;
import ru.sber.entities.Product;
import ru.sber.services.CartService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * Контроллер для обработки запросов к корзине с товарами
 */
@Slf4j
@RestController
@RequestMapping("carts")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CartController {
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{cartId}/products/{productId}")
    public ResponseEntity<?> addProductToCart(@PathVariable long cartId, @PathVariable long productId) throws URISyntaxException {
        log.info("Товар с id {} добавляется в корзину с id {}", productId, cartId);
        cartService.addProductById(cartId, productId);

        return ResponseEntity
                .created(new URI("http://localhost:8080/carts"))
                .build();
    }

    @PutMapping("/{cartId}/products/{productId}")
    public ResponseEntity<?> changeProductQuantity(@PathVariable long cartId, @PathVariable long productId, @RequestBody Product product) {
        long quantity = product.getQuantity();
        log.info("Изменяется количество товара с id {} в корзине с id {} на значение {}", productId, cartId, quantity);

        boolean quantityChanged = cartService.changeQuantity(cartId, productId, quantity);
        HttpStatus status = quantityChanged ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return ResponseEntity.status(status).body(quantityChanged);
    }

    @DeleteMapping("/{cartId}/products/{productId}")
    public ResponseEntity<String> deleteProductFromCart(@PathVariable long cartId, @PathVariable long productId) {
        boolean deleted = cartService.deleteProductFromCart(cartId, productId);
        if (deleted) {
            return ResponseEntity.ok("Товар удален из корзины");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/payment/{cartId}")
    public Optional<Payment> processPayment(@PathVariable long cartId) {
        log.info("Проходит оплата корзины с id {}", cartId);
        return cartService.payment(cartId);
    }

    @GetMapping("{cartId}")
    public List<Product> getCartById(@PathVariable long cartId) {
        log.info("Корзина с id {} получена", cartId);
        return cartService.getCartById(cartId);
    }
}
