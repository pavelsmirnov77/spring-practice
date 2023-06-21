package ru.sber.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sber.repositories.DataBaseRepository;
import ru.sber.services.TransferService;
import java.math.BigDecimal;

/**
 * Класс пользовательского приложения, с помощью которого пользователь осуществляет денежный перевод пользователю банка
 * Содержит в себе два поля, один конструктор с двумя параметрами и метод run для тестирования
 */
@Component
public class App {
    private final TransferService transferService;
    private final DataBaseRepository dataBaseRepository;

    @Autowired
    public App(TransferService transferService, DataBaseRepository dataBaseRepository) {
        this.transferService = transferService;
        this.dataBaseRepository = dataBaseRepository;
    }

    public void run() {
        //Перевод пользователю банка
        transferService.transaction("89673283921", BigDecimal.valueOf(6000));
        //Перевод не пользователю банка
        transferService.transaction("89473783273", BigDecimal.valueOf(3000));
        //Перевод отрицательной суммы
        transferService.transaction("89673283921", BigDecimal.valueOf(-1000));
        //Вывод истории платежей
        dataBaseRepository.getList();
    }
}