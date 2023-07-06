package ru.sber.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Сущность клиента банка
 */
@Data
@AllArgsConstructor
public class ClientBank {
    private long clientId;
    private BigDecimal balance;
}
