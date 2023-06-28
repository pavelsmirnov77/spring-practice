package ru.sber.proxies;

import org.springframework.stereotype.Component;
import ru.sber.entities.ClientBank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Прокси для получения банковской информации клиента
 */
@Component
public class BankAppProxy implements BankAppInterfaceProxy {
    private List<ClientBank> clientsBank;

    public BankAppProxy() {
        this.clientsBank = new ArrayList<>(List.of(
                new ClientBank(0, BigDecimal.valueOf(10000))
        ));
    }

    /**
     * Возвращает баланс клиента
     * @param clientId id клиента
     * @return баланс клиента
     */
    @Override
    public BigDecimal getBalanceClient(long clientId) {
        for (ClientBank clientBank : clientsBank) {
            if (clientBank.getClientId() == clientId) {
                return clientBank.getBalance();
            }
        }
        return BigDecimal.ZERO;
    }
}
