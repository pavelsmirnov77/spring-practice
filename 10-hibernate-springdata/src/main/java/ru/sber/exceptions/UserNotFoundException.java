package ru.sber.exceptions;

public class UserNotFoundException extends RuntimeException {
    /**
     * Исключение для проверки наличия пользователя
     * @param message сообщение об ошибке
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}