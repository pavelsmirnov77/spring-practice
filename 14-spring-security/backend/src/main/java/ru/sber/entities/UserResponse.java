package ru.sber.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Сущность пользователя с неполной информацией
 */
@Data
@AllArgsConstructor
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
