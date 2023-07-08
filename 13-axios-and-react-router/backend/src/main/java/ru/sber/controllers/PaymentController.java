package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.Payment;
import ru.sber.services.PaymentService;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("payment")
public class PaymentController {
    PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * Совершает платеж
     *
     * @param payment сущность платежа
     * @return true если платеж прошёл успешно, иначе false
     */
    @PostMapping
    public ResponseEntity<?> payment(@RequestBody Payment payment) {

        log.info("Попытка совершения оплаты");
        boolean isPay = paymentService.pay(payment);

        if (isPay) {
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.badRequest().body("Оплата не прошла");
        }

    }
}
