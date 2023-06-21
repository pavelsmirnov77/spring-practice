package ru.sber.models;

/**
 * Класс для описания клиента, которому осуществляется перевод
 * Содержит два поля, один конструктор с двумя параметрами и геттеры для полей
 */
public class Client {
    private String clientName;
    private String clientNumber;

    public Client(String clientName, String clientNumber) {
        this.clientName = clientName;
        this.clientNumber = clientNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientNumber() {
        return clientNumber;
    }
}
