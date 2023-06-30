package ru.sber.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "products_carts", schema = "products_smirnov_pa")
public class ProductCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_product", referencedColumnName = "id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_cart", referencedColumnName = "id", nullable = false)
    private Cart cart;

    @Column(name = "count", nullable = false)
    private long quantity;

    // Геттеры и сеттеры

}
