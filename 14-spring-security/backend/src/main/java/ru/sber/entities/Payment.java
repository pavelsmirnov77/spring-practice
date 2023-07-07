package ru.sber.entities;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Сущность платежа
 */
@Data
@AllArgsConstructor
public class Payment {
    @Column(nullable = false)
    Long cardNumber;

    @Column(nullable = false)
    Long userId;
}
