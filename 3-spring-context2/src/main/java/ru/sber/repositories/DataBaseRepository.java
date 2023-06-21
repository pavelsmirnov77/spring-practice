package ru.sber.repositories;

import org.springframework.stereotype.Component;
import ru.sber.models.Client;
import ru.sber.models.Payment;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, реализующий базу данных с историей переводов
 * Содержит методы добавления платежа в лист и метод вывода листа с историей платежей
 */
@Component
public class DataBaseRepository implements BankRepository{
    List<Payment> paymentList = new ArrayList<>();

    public void addPayment(Payment payment) {
        paymentList.add(payment);
    }

    @Override
    public List<Payment> getList() {
        System.out.println("Вывод истории платежей: ");
        for (Payment payment : paymentList) {
            System.out.println(payment);
        }
        return paymentList;
    }
}
