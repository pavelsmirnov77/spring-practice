package ru.sber.services;

import ru.sber.entities.Payment;
import ru.sber.entities.Product;

import java.util.List;
import java.util.Optional;

public interface CartService {
    /**
     * Добавляет товар в корзину по id
     * @param cartId id корзины
     * @param productId id товара
     */
    void addProductById(long cartId, long productId);

    /**
     * Изменяет количество товара в корзине
     * @param cartId id корзины
     * @param productId id продукта
     * @param quantity количество товара
     * @return true, если удачно обновлено значение, иначе false
     */
    boolean changeQuantity(long cartId, long productId, long quantity);

    /**
     * Удаляет товар из корзины
     * @param cartId id корзины
     * @param productId id товара
     * @return true, если товар удален успешно, иначе false
     */
    boolean deleteProductFromCart(long cartId, long productId);

    /**
     * Осуществляет оплату корзины
     * @param cartId id корзины
     * @return объект платежа
     */
    Optional<Payment> payment(long cartId);

    /**
     * Получает корзины по ее id
     *
     * @param cartId id корзины
     * @return объект корзины
     */
    List<Product> getCartById(long cartId);
}