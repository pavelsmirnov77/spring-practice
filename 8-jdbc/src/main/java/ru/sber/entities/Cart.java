package ru.sber.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Сущность корзины товаров клиента
 */
@Data
@AllArgsConstructor
public class Cart {
    private long id;
    private List<Product> products;
    private String promo;
}
