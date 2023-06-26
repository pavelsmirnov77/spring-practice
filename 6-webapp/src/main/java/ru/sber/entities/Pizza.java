package ru.sber.entities;

import java.math.BigDecimal;

public record Pizza(long id, String name, String image, double weight, int size, BigDecimal price) {
}
