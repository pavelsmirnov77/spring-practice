package ru.sber.entities;

import lombok.Data;

import java.util.List;

/**
 * Пользователь с ограниченным количеством полей
 */
@Data
public class UserResponse {
    private long id;
    private String name;
    private String email;
    private List<Product> cart;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getUsername();
        this.email = user.getEmail();
    }
}
