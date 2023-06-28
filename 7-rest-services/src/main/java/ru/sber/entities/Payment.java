package ru.sber.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Payment {
    private BigDecimal amountBuy;
    private BigDecimal clientBalance;
}
