package ru.sber.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, которое выбрасывается, если пользователь не найден
 */
public class UserNotFoundException extends RuntimeException {
    /**
     * Выбрасывает сообщение если пользователь не найден
     *
     * @param message сообщен еоб ошибке
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
