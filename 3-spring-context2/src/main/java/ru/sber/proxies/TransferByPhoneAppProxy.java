package ru.sber.proxies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sber.repositories.DataBaseRepository;
import ru.sber.models.Payment;

import java.math.BigDecimal;

/**
 * Класс, реализующий перевод денежных средств.
 * Содержит в себе поле dataBase, конструктор с одним параметром и метод sendTransfer для перевода
 */
@Component
public class TransferByPhoneAppProxy {
    private final DataBaseRepository dataBaseRepository;

    @Autowired
    public TransferByPhoneAppProxy(DataBaseRepository dataBaseRepository) {
        this.dataBaseRepository = dataBaseRepository;
    }
    public void sendTransfer(String clientNumber, BigDecimal sum) {
        if (sum.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Ошибка: сумма перевода должна быть положительной");
            return;
        }
        Payment payment  = new Payment(clientNumber, sum);
        System.out.printf("Перевод клиенту %s на сумму %s руб. выполнен %n", payment.getClientName(), sum);
        dataBaseRepository.addPayment(payment);
    }
}
