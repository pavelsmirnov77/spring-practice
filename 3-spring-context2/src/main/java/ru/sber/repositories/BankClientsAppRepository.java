package ru.sber.repositories;

import ru.sber.models.Client;

import java.util.List;

/**
 * Класс банковского приложения для предоставления информации о клиентах
 * Содержит переопределенный метод для получения клиентов банка и метод для проверки пользователей
 */
public class BankClientsAppRepository implements BankRepository {

    @Override
    public List<Client> getList() {
        return List.of(
                new Client("Владислав Семенович А", "89673283921"),
                new Client("Дмитрий Журавлёв В", "89324374839")
        );
    }

    public boolean isClientExist(String clientNumber) {
        List<Client> clients = getList();
        for (Client client : clients) {
            if (client.getClientNumber().equals(clientNumber)) {
                return true;
            }
        }
        return false;
    }
}
