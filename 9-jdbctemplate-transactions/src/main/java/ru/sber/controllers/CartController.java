//package ru.sber.controllers;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import ru.sber.entities.Cart;
//import ru.sber.entities.Payment;
//import ru.sber.repositories.CartRepository;
//
//import java.util.Optional;
//
///**
// * Контроллер для обработки запросов к корзине с товарами
// */
//@Slf4j
//@RestController
//@RequestMapping("carts")
//public class CartController {
//    private final CartRepository cartRepository;
//
//    @Autowired
//    public CartController(CartRepository cartRepository) {
//        this.cartRepository = cartRepository;
//    }
//
//    @PostMapping("/{cartId}/products/{productId}")
//    public void addProductToCart(@PathVariable long cartId, @PathVariable long productId) {
//        log.info("Товар с id {} добавлен в корзину с id {}", productId, cartId);
//        cartRepository.addProductById(cartId, productId);
//    }
//
//    @PutMapping("/{cartId}/products/{productId}")
//    public boolean changeProductQuantity(@PathVariable long cartId, @PathVariable long productId, @RequestParam long quantity) {
//        log.info("У товара с id {} в корзине с id {} изменено количество на значение {}", productId, cartId, quantity);
//        return cartRepository.changeQuantity(cartId, productId, quantity);
//    }
//
//    @DeleteMapping("/{cartId}/products/{productId}")
//    public ResponseEntity<?> deleteProductFromCart(@PathVariable long cartId, @PathVariable long productId) {
//        boolean isDeleted = cartRepository.deleteProductFromCart(cartId, productId);
//
//        if (isDeleted) {
//            log.info("Товар с id {} удален из корзины с id {}", productId, cartId);
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PostMapping("/payment/{cartId}")
//    public Optional<Payment> processPayment(@PathVariable long cartId) {
//        log.info("Проходит оплата корзины с id {}", cartId);
//        return cartRepository.payment(cartId);
//    }
//
//    @GetMapping("/{cartId}")
//    public Optional<Cart> getCartById(@PathVariable long cartId) {
//        log.info("Корзина с id {} получена", cartId);
//        return cartRepository.getCartById(cartId);
//    }
//}
