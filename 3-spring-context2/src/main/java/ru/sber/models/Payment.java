package ru.sber.models;

import ru.sber.repositories.BankClientsAppRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Класс, описывающий денежный перевод
 * Содержит два поля, одну приватную переменную bankClientsApp для работы с клиентами, один конструктор с двумя параметрами,
 * а также методы для получения имени пользователя по номеру телефона и переопределенный метод toString для вывода платежа
 */
public class Payment {
    private String clientNumber;
    private BigDecimal sum;

    private BankClientsAppRepository bankClientsAppRepository = new BankClientsAppRepository();

    public Payment(String clientNumber, BigDecimal sum) {
        this.clientNumber = clientNumber;
        this.sum = sum;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public BigDecimal getSum() {
        return sum;
    }

    //Метод для поиска имени клиента по номеру телефона
    public String getClientName() {
        Client client = findClientByNumber(clientNumber);
        return client.getClientName();
    }

    //Метод для определения клиента по номеру телефона
    private Client findClientByNumber(String clientNumber) {
        List<Client> clientList = bankClientsAppRepository.getList();
        for (Client client : clientList) {
            if (client.getClientNumber().equals(clientNumber)) {
                return client;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Клиент: " + getClientName() + ", сумма перевода: " + getSum() + " руб.";
    }
}
