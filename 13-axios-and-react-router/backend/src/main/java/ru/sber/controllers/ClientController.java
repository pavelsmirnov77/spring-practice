package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.Client;
import ru.sber.services.ClientService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Контроллер для обработки запросов к клиентам интернет магазина
 */
@Slf4j
@RestController
@RequestMapping("clients")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<?> registration(@RequestBody Client client) throws URISyntaxException {
        log.info("Проходит регистрация клиента {}", client);
        long id = clientService.registrationClient(client);

        return ResponseEntity
                .created(new URI("http://localhost:8080/clients/" + id))
                .build();
    }

    @GetMapping
    public List<Client> findAllClients(@RequestParam(required = false) Long clientId) {
        log.info("Получение клиентов");

        return clientService.findAllClients(clientId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable long id) {
        boolean isDeleted = clientService.deleteClientById(id);

        if (isDeleted) {
            log.info("Клиент с id {} удалён", id);
            return ResponseEntity.noContent().build();
        } else {
            log.info("Клиент с id {} не найден", id);
            return ResponseEntity.notFound().build();
        }
    }
}
