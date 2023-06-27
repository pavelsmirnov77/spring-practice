package ru.sber.services;

import ru.sber.entities.Pizza;

import java.util.List;

public interface PizzaInterfaceService {
    List<Pizza> searchPizzas(String namePizza);
    void addPizzaToSelected(int id);
}
