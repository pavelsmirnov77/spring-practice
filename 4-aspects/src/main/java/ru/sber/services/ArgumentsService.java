package ru.sber.services;

import org.springframework.stereotype.Service;
import ru.sber.aspects.NotEmpty;
import ru.sber.models.Animals;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Сервис, реализующий методы с аннотацией @NotEmpty
 */
@Service
public class ArgumentsService {

    private Logger logger = Logger.getLogger(ArgumentsService.class.getName());

    List<Animals> animalsList = List.of(
            new Animals("Собака", "Бобик", 5),
            new Animals("Кот", "Тима", 10),
            new Animals("Попугай", "Цыпа", 7)
    );

    @NotEmpty
    public void methodWithStringArgs(String name) {
        Animals animal = animalsList.stream()
                .filter(a -> a.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
        if (animal != null) {
            logger.info("Найдено животное: вид: " + animal.getType() +
                    ", имя: " + animal.getName() +
                    ", возраст: " + animal.getAge());
        } else {
            logger.info("Животное не найдено");
        }
    }

    @NotEmpty
    public void methodWithIntegerArgs(Integer age) {
        Animals animal = animalsList.stream()
                .filter(a -> Objects.equals(a.getAge(), age))
                .findFirst()
                .orElse(null);
        if (animal != null) {
            logger.info("Найдено животное: вид: " + animal.getType() +
                    ", имя: " + animal.getName() +
                    ", возраст: " + animal.getAge());
        } else {
            logger.info("Животное не найдено");
        }
    }

    @NotEmpty
    public void methodWithCollectionArgs(List<Animals> animals) {
        logger.info("Список животных: " + animals.toString());
    }

    public void methodWithoutAnnotation(String animalType) {
        List<Animals> filteredAnimals = animalsList.stream()
                .filter(animal -> animal.getType().equalsIgnoreCase(animalType))
                .toList();
        if (filteredAnimals.isEmpty()) {
            logger.info("Животные не найдены");
        } else {
            logger.info("Животные вида " + animalType + ": " + filteredAnimals);
        }
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
