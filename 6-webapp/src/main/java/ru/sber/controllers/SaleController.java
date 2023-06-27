package ru.sber.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sber.entities.Pizza;
import ru.sber.services.PizzaService;

import java.math.BigDecimal;
import java.util.List;

/**
 * Контроллер для обработки запросов к странице с покупками и добавления пицц в корзину
 */
@Controller
public class SaleController {
    private final PizzaService pizzaService;

    /**
     * Конструктор контроллера
     * @param pizzaService Сервис пицц, который реализует поиск пицц и их добавление
     */
    public SaleController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    /**
     * Возвращает веб-страницу sales.html
     * @param model модели добавленных пицц, итогового количества пицц и итоговой стоимости
     * @param session сессия для хранения общего количества пицц
     * @return веб-страницу sales.html
     */
    @GetMapping("/sales")
    public String getSalesPage(Model model,  HttpSession session) {
        List<Pizza> selectedPizzas = pizzaService.getSelectedPizzas();
        int totalQuantity = selectedPizzas.size();
        BigDecimal totalPrice = selectedPizzas.stream()
                .map(Pizza::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("selectedPizzas", selectedPizzas);
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("totalPrice", totalPrice);

        session.setAttribute("totalQuantity", totalQuantity);

        return "sales";
    }

    /**
     * Добавляет выбранную пиццу в корзину и возвращает веб-страницу sales.html
     * @param pizzaId id пиццы
     * @return веб-страницу sales.html
     */
    @PostMapping("/sales")
    public String addPizzaToSales(@RequestParam("pizzaId") int pizzaId) {
        pizzaService.addPizzaToSelected(pizzaId);
        return "redirect:/sales";
    }
}
