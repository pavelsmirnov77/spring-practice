package ru.sber.repositories;

import org.springframework.stereotype.Repository;
import ru.sber.entities.Client;
import ru.sber.entities.ClientResponse;
import ru.sber.exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Репозиторий, выполняющий действия над клиентом интернет магазина
 */
@Repository
public class LocalClientRepository implements ClientRepository {

    private final LocalCartRepository localCartRepository;
    private List<Client> clients;

    public LocalClientRepository(LocalCartRepository localCartRepository) {
        this.localCartRepository = localCartRepository;
        this.clients = new ArrayList<>(List.of(
                new Client(0, "Павел", "pavelsmir", "89uip12", "pavel@yandex.ru", localCartRepository.getCartById(1))
        ));
    }

    /**
     * Добавляет клиента в список зарегистрированных клиентов
     * @param client объект клиента
     * @return id зарегистрированного клиента
     */
    @Override
    public long registrationClient(Client client) {
        long id = generateId();
        client.setId(id);
        clients.add(client);

        return id;
    }

    /**
     * Получает объект клиента с неполной информацией по
     * @param clientResponseId id клиента с неполной информацией
     * @return объект клиента
     */
    @Override
    public ClientResponse getClientResponseById(long clientResponseId) {
        Optional<Client> clientOptional = clients.stream()
                .filter(client -> client.getId() == clientResponseId)
                .findFirst();

        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            return new ClientResponse(client.getId(), client.getName(), client.getCart());
        } else {
            throw new UserNotFoundException("Пользователь с id: " + clientResponseId + " не найден");
        }
    }



    /**
     * Удаляет клиента оп id
     * @param clientId id клиента
     * @return true, если успешно удален, иначе false
     */
    @Override
    public boolean deleteClientById(long clientId) {
        return clients.removeIf(client -> client.getId() == clientId);
    }

    /**
     * Вовзращает список всех клиентов
     * @return список всех клиентов
     */
    @Override
    public List<Client> findAll() {
        return clients;
    }

    /**
     * Генерирует случайный id
     * @return случайное число
     */
    private long generateId() {
        Random random = new Random();
        int low = 1;
        int high = 1_000_000;
        return random.nextLong(high - low) + low;
    }
}
