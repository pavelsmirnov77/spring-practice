package ru.sber.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sber.entities.Pizza;
import ru.sber.services.PizzaService;
import java.util.List;

/**
 * Контроллер для обработки запросов к главной странице и поиска пицц по названию
 */
@Controller
public class PizzaController {
    private final PizzaService pizzaService;

    /**
     * Конструктор контроллера
     * @param pizzaService Сервис пицц, который реализует поиск пицц и их добавление
     */
    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    /***
     * Возвращает в качестве ответа на запрос веб-страницу
     * @return веб-страницу страницу main.html
     */
    @GetMapping("/")
    public String homePage(Model model) {
        List<Pizza> pizzas = pizzaService.getAllPizzas();
        model.addAttribute("pizzas", pizzas);
        return "main";
    }

    /**
     * Обработывает поиск пиццы по названию
     * @param searchTerm имя искомой пиццы
     * @param model модель найденных пицц
     * @return веб-страницу main.html
     */
    @GetMapping("/search")
    public String searchPizzas(@RequestParam("searchTerm") String searchTerm, Model model) {
        List<Pizza> searchResults = pizzaService.searchPizzas(searchTerm);
        model.addAttribute("pizzas", searchResults);
        return "main";
    }
}
