package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.Client;
import ru.sber.entities.ClientResponse;
import ru.sber.exceptions.UserNotFoundException;
import ru.sber.repositories.ClientRepository;

import java.net.URI;

/**
 * Контроллер для обработки запросов к клиентам интернет магазина
 */
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
    public ResponseEntity<?> addClient(@RequestBody Client client) {
        log.info("Регистрация клиента {}", client);

        return  ResponseEntity.created(URI.create("clientId/" + clientRepository.registrationClient(client))).build();
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<?> getClientResponseById(@PathVariable long clientId) {
        try {
            log.info("Клиент с id {} получен", clientId);
            ClientResponse clientResponse = clientRepository.getClientResponseById(clientId);
            return ResponseEntity.ok(clientResponse);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable long id) {
        boolean isDeleted = clientRepository.deleteClientById(id);

        if (isDeleted) {
            log.info("Клиент с id {} удалён", id);
            return ResponseEntity.noContent().build();
        } else {
            log.info("Клиент с id {} не найден", id);
            return ResponseEntity.notFound().build();
        }
    }
}
