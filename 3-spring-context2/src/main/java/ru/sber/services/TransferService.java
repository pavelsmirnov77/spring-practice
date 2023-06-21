package ru.sber.services;

import org.springframework.stereotype.Service;
import ru.sber.repositories.DataBaseRepository;
import ru.sber.proxies.TransferByPhoneAppProxy;
import ru.sber.repositories.BankClientsAppRepository;

import java.math.BigDecimal;

/**
 * Класс приложения, которое осуществляет перевод денежных средств по номеру телефона
 * Содержит одну приватную переменную - экземпляр класса BankClientsApp, два поля, один конструктор с двумя параметрами
 * и метод для перевода денежных средств
 */
@Service
public class TransferService {
    private final BankClientsAppRepository bankClientsAppRepository = new BankClientsAppRepository();
    private final DataBaseRepository dataBaseRepository;
    private final TransferByPhoneAppProxy transferByPhoneAppProxy;

    public TransferService(DataBaseRepository dataBaseRepository, TransferByPhoneAppProxy transferByPhoneAppProxy) {
        this.dataBaseRepository = dataBaseRepository;
        this.transferByPhoneAppProxy = transferByPhoneAppProxy;
    }

    public void transaction(String clientNumber, BigDecimal sum) {
        if(bankClientsAppRepository.isClientExist(clientNumber)) {
            transferByPhoneAppProxy.sendTransfer(clientNumber, sum);
        } else {
            System.out.println("Ошибка: пользователь не является клиентом банка");
        }
    }
}
