package ru.sber.exceptions;

public class ProductsException extends RuntimeException {
    /**
     * Выбрасывает сообщение при неудачном взаимодействии с товаром
     *
     * @param message сообщен еоб ошибке
     */
    public ProductsException(String message) {
        super(message);
    }
}