package ru.sber.repositories;

import ru.sber.entities.Cart;

public interface CartRepository {
    void addProductById(long cartId, long productId);

    boolean changeQuantity(long cartId, long productId, long quantity);

    boolean deleteProductFromCart(long cartId, long productId);

    boolean payment(long cartId);

    Cart getCartById(long cartId);
}
