package ru.sber.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для обработки запросов к страницам с авторизацией и регистрацией
 */
@Controller
public class UserController {

    /**
     * Возвращает веб-страницу login.html
     * @return веб-страницу login.html
     */
    @GetMapping("/login")
    public String authorization() {
        return "login";
    }

    /**
     * Возвращает веб-страницу registration.html
     * @return веб-страницу registration.html
     */
    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }
}
