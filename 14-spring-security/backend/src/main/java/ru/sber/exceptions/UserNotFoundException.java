package ru.sber.exceptions;

public class UserNotFoundException extends RuntimeException {
    /**
     * Выбрасывает сообщение если пользователь не найден
     *
     * @param message сообщение об ошибке
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
