package ru.sber.services;

import org.springframework.stereotype.Service;
import ru.sber.entities.Pizza;

import java.math.BigDecimal;
import java.util.*;

/**
 * Сервис для поиска нужной пиццы и добавления пиццы и получения всего списка пицц
 */
@Service
public class PizzaService implements PizzaInterfaceService {

    private List<Pizza> pizzas = new ArrayList<>(List.of(
            new Pizza(1, "Пепперони", "https://cdpiz1.pizzasoft.ru/rs/280x280/pizzafab/items/3/pepperoni-bolshaya-main_image-3612-77066.jpg", 900, 30, BigDecimal.valueOf(700)),
            new Pizza(2, "Маргарита", "https://cdpiz1.pizzasoft.ru/rs/280x280/pizzafab/items/3/margarita-bolshaya-main_image-3587-77377.jpg", 780, 30, BigDecimal.valueOf(760)),
            new Pizza(3, "Ветчина и грибы", "https://cdpiz1.pizzasoft.ru/rs/280x280/pizzafab/items/3/vetchina-i-griby-bolshaya-main_image-3472-36351.jpg", 820, 30, BigDecimal.valueOf(680)),
            new Pizza(4, "Деревенская", "https://cdpiz1.pizzasoft.ru/rs/280x280/pizzafab/items/3/derevenskaya-bolshaya-main_image-3506-42148.jpg", 800, 30, BigDecimal.valueOf(800)),
            new Pizza(5, "Барбекю", "https://cdpiz1.pizzasoft.ru/rs/280x280/pizzafab/items/7/barbekyu-bolshaya-main_image-7176-28721.jpg", 830, 30, BigDecimal.valueOf(790)),
            new Pizza(6, "Баварская", "https://cdpiz1.pizzasoft.ru/rs/280x280/pizzafab/items/3/bavarskaya-bolshaya-main_image-3441-96931.jpg", 775, 30, BigDecimal.valueOf(850))
    ));
    private List<Pizza> selectedPizzas = new ArrayList<>();

    /**
     * Ищет пиццу по названию
     * @param searchTerm название нужной пиццы
     * @return список найденных пицц
     */
    @Override
    public List<Pizza> searchPizzas(String searchTerm) {
        List<Pizza> searchResults = new ArrayList<>();
        for (Pizza pizza : pizzas) {
            if (pizza.name().equalsIgnoreCase(searchTerm)) {
                searchResults.add(pizza);
            }
        }
        if (Objects.equals(searchTerm, "")) {
            return pizzas;
        }
        return searchResults;
    }

    /**
     * Получает пиццу по id
     * @param pizzaId id пиццы
     * @return объект пиццы
     */
    public Pizza getPizzaById(int pizzaId) {
        Optional<Pizza> optionalPizza = pizzas.stream()
                .filter(pizza -> pizza.id() == pizzaId)
                .findFirst();
        return optionalPizza.orElse(null);
    }

    /**
     * Добавляет пиццу по id
     * @param pizzaId id пиццы
     */
    @Override
    public void addPizzaToSelected(int pizzaId) {
        Pizza selectedPizza = getPizzaById(pizzaId);
        if (selectedPizza != null) {
            selectedPizzas.add(selectedPizza);
        }
    }

    /**
     * Получает найденные пиццы
     * @return список найденных пицц
     */
    public List<Pizza> getSelectedPizzas() {
        return selectedPizzas;
    }

    /**
     * Получает список всех пицц
     * @return список всех пицц
     */
    public List<Pizza> getAllPizzas() {
        return pizzas;
    }
}
