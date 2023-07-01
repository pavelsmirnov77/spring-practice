package ru.sber.services;

import ru.sber.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductServiceImpl {
    /**
     * Добавляет товар
     * @param product объект товара
     * @return id товара
     */
    long createProduct(Product product);

    /**
     * Изменяет название и цену товара
     * @param product объект товара
     * @return true, если товар изменен улачно, иначе false
     */
    boolean changeProduct(Product product);

    /**
     * Удаляет товар по id
     * @param productId id товара
     * @return true, если товар удален успешно, иначе false
     */
    boolean deleteProductById(long productId);

    /**
     * Ищет товары по названию
     * @param productName название товара
     * @return список товаров с заданным названием
     */
    List<Product> findProductByName(String productName);

    /**
     * Получение товара по id
     * @param productId id товара
     * @return объект полученного товара
     */
    Optional<Product> getProductById(long productId);
}
