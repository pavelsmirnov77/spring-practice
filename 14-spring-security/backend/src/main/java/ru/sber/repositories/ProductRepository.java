package ru.sber.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.entities.Product;

import java.util.List;

/**
 * Хранилище с данными о товарах
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Ищет все товары, содержащие в названии определенную строку
     *
     * @param name Строка, по которой идет поиск
     * @return Возвращает полученный список товаров
     */
    List<Product> findAllByNameContainingIgnoreCase(String name);
}
