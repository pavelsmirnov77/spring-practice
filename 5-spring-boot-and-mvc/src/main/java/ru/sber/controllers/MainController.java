package ru.sber.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для обработки входящего запроса
 */
@Controller
public class MainController {
    /***
     * Возвращает в качестве ответа на запрос веб-страницу
     * @return веб-страницу страницу main.html
     */
    @GetMapping("/home_page")
    public String homePage() {
        return "pages/main.html";
    }
}
