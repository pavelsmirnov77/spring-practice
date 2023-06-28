package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.Client;
import ru.sber.entities.ClientResponse;
import ru.sber.repositories.ClientRepository;

@Slf4j
@RestController
@RequestMapping("clients")
public class ClientController {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @PostMapping
    public long addClient(@RequestBody Client client) {
        log.info("Регистрация клиента {}", client);

        return clientRepository.registrationClient(client);
    }

    @GetMapping("/{clientId}")
    public ClientResponse getClientResponseById(@PathVariable long clientId) {
        log.info("Клиент с id {} получен", clientId);
        return clientRepository.getClientResponseById(clientId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
        boolean isDeleted = clientRepository.deleteClientById(id);

        if (isDeleted) {
            log.info("Товар с id {} удалён", id);
            return ResponseEntity.noContent().build();
        } else {
            log.info("Товар с id {} не найден", id);
            return ResponseEntity.notFound().build();
        }
    }
}
