package ru.sber.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность товаров в корзине
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products_carts")
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
}
