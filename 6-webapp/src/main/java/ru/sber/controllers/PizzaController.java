package ru.sber.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sber.entities.Pizza;
import ru.sber.services.PizzaService;
import java.util.List;

/**
 * Контроллер для обработки входящего запроса
 */
@Controller
public class PizzaController {
    @Autowired
    private PizzaService pizzaService;
    /***
     * Возвращает в качестве ответа на запрос веб-страницу
     * @return веб-страницу страницу main.html
     */
    @GetMapping("/")
    public String homePage(Model model) {
        List<Pizza> pizzas = pizzaService.findAll();
        model.addAttribute("pizzas", pizzas);
        return "main";
    }

    @GetMapping("/search")
    public String searchPizzas(@RequestParam("searchTerm") String searchTerm, Model model) {
        List<Pizza> searchResults = pizzaService.searchPizzasByName(searchTerm);
        model.addAttribute("pizzas", searchResults);
        return "main";
    }
}
