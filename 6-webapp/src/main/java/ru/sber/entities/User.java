package ru.sber.entities;

/**
 * Сущность зарегистрированного пользователя
 * @param id id пользователя
 * @param name имя пользователя
 * @param phoneNumber номер телефона пользователя
 * @param email электронная почта пользователя
 * @param password пароль
 */
public record User(long id, String name, String phoneNumber, String email, String password) {
}
