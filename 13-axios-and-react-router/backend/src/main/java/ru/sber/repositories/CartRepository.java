package ru.sber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.entities.Cart;

import java.util.List;
import java.util.Optional;

/**
 * Хранилище данных о корзине
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    /**
     * Находит корзину по id товара и id пользователя
     *
     * @param productId id товара
     * @param userId    id пользователя
     * @return найденную корзину
     */
    Optional<Cart> findCartByProduct_IdAndClient_Id(long productId, long userId);

    /**
     * Удаляет из БД все записи о корзине пользователя
     *
     * @param userId Уникальный идентификатор пользователя
     */
    void deleteCartByClientId(long userId);

    /**
     * Находит в БД корзину по id клиента
     *
     * @param userId Уникальный идентификатор пользователя
     * @return Возвращает найденную корзину
     */
    List<Cart> findCartByClient_Id(long userId);

    /**
     * Считает, сколько товаров в корзине у пользователя
     *
     * @param userId Уникальный идентификатор пользователя
     * @return Возвращает количество товаров
     */
    int countCartsByClient_Id(long userId);

    /**
     * Подсчитывает количество корзин, в которых есть определенных товар
     *
     * @param productId Уникальный идентификатор товара
     * @return Возвращает количество корзин
     */
    int countCartsByProduct_Id(long productId);

}
