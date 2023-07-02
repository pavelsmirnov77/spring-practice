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
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<ProductCart> productCarts;
    @Column(nullable = false)
    private String promocode;
}
