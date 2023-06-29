package ru.sber.exceptions;

public class ProductNotFoundException extends RuntimeException {
    /**
     * Исключение для проверки наличия товара
     * @param message сообщение об ошибке
     */
    public ProductNotFoundException(String message) {
        super(message);
    }
}