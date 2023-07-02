package ru.sber.proxies;

import org.springframework.stereotype.Component;
import ru.sber.entities.ClientBank;
import ru.sber.exceptions.PaymentException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Прокси банковского приложения для получения и изменения баланса клиентов
 */
@Component
public class BankAppProxy implements BankAppInterfaceProxy {
    List<ClientBank> clientsBank = new ArrayList<>();

    @Override
    public BigDecimal getBalance(long clientId) {
        for(ClientBank clientBank : clientsBank) {
            if (clientBank.getClientId() == clientId) {
                return clientBank.getBalance();
            }
        }
        throw new PaymentException("Оплата не прошла");
    }

    @Override
    public void setBalance(long clientId, BigDecimal newBalance) {
        for(ClientBank clientBank : clientsBank) {
            if (clientBank.getClientId() == clientId) {
                clientBank.setBalance(newBalance);
            }
        }
    }

    @Override
    public void addClient(ClientBank client) {
        clientsBank.add(client);
    }
}
