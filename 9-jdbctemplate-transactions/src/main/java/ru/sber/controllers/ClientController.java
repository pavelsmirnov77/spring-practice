//package ru.sber.controllers;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import ru.sber.entities.Client;
//import ru.sber.entities.ClientResponse;
//import ru.sber.exceptions.UserNotFoundException;
//import ru.sber.repositories.ClientRepository;
//
///**
// * Контроллер для обработки запросов к клиентам интернет магазина
// */
//@Slf4j
//@RestController
//@RequestMapping("clients")
//public class ClientController {
//
//    private final ClientRepository clientRepository;
//
//    @Autowired
//    public ClientController(ClientRepository clientRepository) {
//        this.clientRepository = clientRepository;
//    }
//
//    @PostMapping
//    public long addClient(@RequestBody Client client) {
//        log.info("Регистрация клиента {}", client);
//
//        return clientRepository.registrationClient(client);
//    }
//
//    @GetMapping("/{clientId}")
//    public ResponseEntity<?> getClientResponseById(@PathVariable long clientId) {
//        try {
//            log.info("Клиент с id {} получен", clientId);
//            ClientResponse clientResponse = clientRepository.getClientResponseById(clientId);
//            return ResponseEntity.ok(clientResponse);
//        } catch (UserNotFoundException ex) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteProduct(@PathVariable long id) {
//        boolean isDeleted = clientRepository.deleteClientById(id);
//
//        if (isDeleted) {
//            log.info("Товар с id {} удалён", id);
//            return ResponseEntity.noContent().build();
//        } else {
//            log.info("Товар с id {} не найден", id);
//            return ResponseEntity.notFound().build();
//        }
//    }
//}
