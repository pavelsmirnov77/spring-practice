package ru.sber.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Сущность корзины товаров клиента
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "products_smirnov_pa", name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String promocode;
}
