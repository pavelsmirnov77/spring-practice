package ru.sber.repositories;

import ru.sber.entities.Cart;
import ru.sber.entities.Payment;

public interface CartRepository {
    void addProductById(long cartId, long productId);

    boolean changeQuantity(long cartId, long productId, long quantity);

    boolean deleteProductFromCart(long cartId, long productId);

    Payment payment(long cartId);

    Cart getCartById(long cartId);
}
