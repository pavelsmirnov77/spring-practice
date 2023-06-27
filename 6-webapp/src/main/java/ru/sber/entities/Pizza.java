package ru.sber.entities;

import java.math.BigDecimal;

/**
 * Сущность пиццы
 * @param id id пиццы
 * @param name название пиццы
 * @param image ссылка на изображение пиццы
 * @param weight вес пиццы
 * @param size диаметр пиццы
 * @param price цена пиццы
 */
public record Pizza(long id, String name, String image, double weight, int size, BigDecimal price) {
}
