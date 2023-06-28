package ru.sber.repositories;

import ru.sber.entities.Client;
import ru.sber.entities.ClientResponse;

import java.util.List;

public interface ClientRepository {
    long registrationClient(Client client);

    ClientResponse getClientResponseById(long clientResponseId);

    boolean deleteClientById(long clientId);

    List<Client> findAll();
}
