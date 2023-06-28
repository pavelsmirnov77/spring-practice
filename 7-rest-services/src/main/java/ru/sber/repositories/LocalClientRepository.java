package ru.sber.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.sber.entities.*;
import ru.sber.exceptions.UserNotFoundException;
import ru.sber.proxies.BankAppProxy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Репозиторий, выполняющий действия над клиентом интернет магазина
 */
@Repository
public class LocalClientRepository implements ClientRepository {
    private LocalCartRepository localCartRepository;
    @Autowired
    private CartRepository cartRepository;
    private List<Client> clients;

    private List<Cart> carts;
    private final BankAppProxy bankAppProxy;

    public LocalClientRepository(LocalCartRepository localCartRepository, BankAppProxy bankAppProxy) {
        this.clients = new ArrayList<>(List.of(
                new Client(1, "Павел", "pavelsmir",
                        "89uip12", "pavel@yandex.ru",
                        localCartRepository.getCartById(1))
        ));
        this.carts = localCartRepository.getAllCarts();
        this.bankAppProxy = bankAppProxy;
    }

    /**
     * Добавляет клиента в список зарегистрированных клиентов
     * @param client объект клиента
     * @return id зарегистрированного клиента
     */
    @Override
    public long registrationClient(Client client) {
        long id = generateId();
        Cart cart = new Cart(generateId(), new ArrayList<>(), "");
        client.setId(id);
        client.setCart(cart);
        clients.add(client);
        bankAppProxy.getAllClients().add(new ClientBank(id, BigDecimal.valueOf(100000)));

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
