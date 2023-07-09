package ru.sber.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Сущность банковской карты
 */
@Data
@AllArgsConstructor
public class BankCard {
    private long number;
    private BigDecimal balance;
}