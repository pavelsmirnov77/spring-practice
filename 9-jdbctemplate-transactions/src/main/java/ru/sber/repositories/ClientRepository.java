package ru.sber.repositories;

import ru.sber.entities.Client;
import ru.sber.entities.ClientResponse;

public interface ClientRepository {
     /**
     * Добавляет клиента в таблицу зарегистрированных клиентов
     * @param client объект клиента
     * @return id зарегистрированного клиента
     */
    long registrationClient(Client client);

     /**
     * Получает объект клиента с неполной информацией по
     * @param clientResponseId id клиента с неполной информацией
     * @return объект клиента
     */
    ClientResponse getClientResponseById(long clientResponseId);

     /**
     * Удаляет клиента оп id
     * @param clientId id клиента
     * @return true, если успешно удален, иначе false
     */
    boolean deleteClientById(long clientId);

}
