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
                new ClientBank(1, BigDecimal.valueOf(1000000))
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

    /**
     * Обновляет значение баланса клиента
     * @param clientId id клиента
     * @param newBalance новое значение баланса
     */
    @Override
    public void setBalanceClient(long clientId, BigDecimal newBalance) {
        for (ClientBank clientBank : clientsBank) {
            if (clientBank.getClientId() == clientId) {
                clientBank.setBalance(newBalance);
            }
        }
    }

    public List<ClientBank> getAllClients() {
        return clientsBank;
    }
}
