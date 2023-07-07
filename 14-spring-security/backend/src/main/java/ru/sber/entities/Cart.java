package ru.sber.entities;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Сущность корзины с товарами
 */
@Entity
@Data
@Table(name = "products_carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @Column(nullable = false)
    private int quantity;
}
