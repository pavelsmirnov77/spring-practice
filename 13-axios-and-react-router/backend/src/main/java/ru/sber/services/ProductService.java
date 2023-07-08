package ru.sber.services;

import ru.sber.entities.Product;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для взаимодействия с товарами
 */
public interface ProductService {

    /**
     * Добавляет новый товар
     *
     * @param product Новый товар
     * @return Возвращает идентификатор добавленного товара
     */
    long addNewProduct(Product product);

    /**
     * Ищет товар по его идентификатору
     *
     * @param id Уникальный идентификатор товара
     * @return Возвращает найденный товар
     */
    Optional<Product> findById(long id);

    /**
     * Ищет товары по их названию
     *
     * @param name Название товара
     * @return Возвращает список найденных товаров
     */
    List<Product> findAllByName(String name);

    /**
     * Изменяет товар
     *
     * @param product Обновленный товар
     * @return Возвращает статус обновления товара
     */
    boolean update(Product product);

    /**
     * Проверяет существование товара
     *
     * @param productId Уникальный идентификатор товара
     * @return Возвращает статус проверки
     */
    boolean checkProductExistence(long productId);

    /**
     * Удаляет товар по идентификатору
     *
     * @param productId Уникальный идентификатор товара
     * @return Возвращает статус удаления товара
     */
    boolean deleteById(long productId);


}
