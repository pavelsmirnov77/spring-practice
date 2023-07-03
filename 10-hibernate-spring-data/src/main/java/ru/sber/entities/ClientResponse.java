package ru.sber.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Сущность клиента с ограниченной информацией
 */
@Data
@AllArgsConstructor
public class ClientResponse {
    private long id;
    private String name;
    private List<Product> cart;
}
