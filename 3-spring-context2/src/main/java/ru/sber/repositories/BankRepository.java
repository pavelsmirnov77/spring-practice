package ru.sber.repositories;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Интерфейс, который объявляет метод для получения всех клиентов банка
 */
@Repository
public interface BankRepository<T> {
    List<T> getList();
}
