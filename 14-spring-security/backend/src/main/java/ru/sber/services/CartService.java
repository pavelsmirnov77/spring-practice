package ru.sber.services;

import ru.sber.entities.Product;

import java.util.List;

/**
 * Сервис для взаимодействия с корзиной пользователя
 */
public interface CartService {
    /**
     * Добавление товара в корзину
     *
     * @param userId    Уникальный идентификатор пользователя
     * @param productId Уникальный идентификатор товара
     * @return Возвращает статус добавления товара в корзину
     */
    boolean addToCart(long userId, long productId);

    /**
     * Изменение количества товара в корзине
     *
     * @param userId    Уникальный идентификатор пользователя
     * @param productId Уникальный идентификатор товара
     * @param amount    Количество добавляемого товара
     * @return Возвращает статус обновления количества товара в корзине
     */
    boolean updateProductAmount(long userId, long productId, int amount);

    /**
     * Удаление товара из корзины
     *
     * @param userId    Уникальный идентификатор пользователя
     * @param productId Уникальный идентификатор товара
     * @return Возвращает статус удаления товара из корзины
     */
    boolean deleteProduct(long userId, long productId);

    /**
     * Полностью очищает корзину пользователя
     *
     * @param userId Уникальный идентификатор пользователя
     */
    void clearCart(long userId);

    /**
     * Выдает список товаров в корзине пользователя
     *
     * @param userId Уникальный идентификатор пользователя
     * @return Возвращает список товаров
     */
    List<Product> getListOfProductsInCart(long userId);

    /**
     * Подсчитывает количество товаров в корзине пользователя
     *
     * @param userId Уникальный идентификатор пользователя
     * @return Возвращает количество товаров
     */
    int countProductsInCart(long userId);

}
