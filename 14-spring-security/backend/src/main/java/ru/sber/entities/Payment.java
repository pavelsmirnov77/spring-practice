package ru.sber.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Информация о платеже
 */
@Data
@AllArgsConstructor
public class Payment {
    Long cardNumber;
    Long userId;
}
