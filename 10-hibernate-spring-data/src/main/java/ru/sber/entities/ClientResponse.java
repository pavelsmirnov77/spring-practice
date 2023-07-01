package ru.sber.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Сущность клиента с ограниченной информацией
 */
@Data
@AllArgsConstructor
public class ClientResponse {
    private long id;
    private String name;
    private Cart cart;
}
