package ru.sber.repositories;

import ru.sber.entities.Cart;
import ru.sber.entities.Payment;

import java.util.Optional;

public interface CartRepository {
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
     * @param cartId id корзины
     * @return объект корзины
     */
    Optional<Cart> getCartById(long cartId);
}
