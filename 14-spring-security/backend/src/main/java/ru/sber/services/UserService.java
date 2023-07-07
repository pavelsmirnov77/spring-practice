package ru.sber.services;

import ru.sber.entities.User;

import java.util.Optional;

public interface UserService {

    /**
     * Регистрирует пользователя
     *
     * @param user Данные о пользователе
     * @return Возвращает идентификатор созданного пользователя
     */
    long signUp(User user);

    /**
     * Производит поиск пользователя по id
     *
     * @param userId Уникальный идентификатор пользователя
     * @return Возвращает найденного пользователя
     */
    Optional<User> getUserById(long userId);

    /**
     * Проверяет существует ли пользователь
     *
     * @param userId Уникальный идентификатор пользователя
     * @return Возвращает результат проверки
     */
    boolean checkUserExistence(long userId);

    /**
     * Удаляет пользователя по id
     *
     * @param userId Уникальный идентификатор пользователя
     * @return Возвращает true при удачном удалении и false, если пользователя не существует
     */
    boolean deleteUserById(long userId);

    Optional<User> findByLoginAndPassword(String login, String password);
}
