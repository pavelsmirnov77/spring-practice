package ru.sber.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sber.config.ProjectConfig;
import ru.sber.models.Animals;
import ru.sber.services.ArgumentsService;

import java.util.List;

/**
 * Класс для тестирования работы аннотации
 */
public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        var service = context.getBean(ArgumentsService.class);

        service.methodWithCollectionArgs(List.of(new Animals("Собака", "Тузик", 5)));
        service.methodWithoutAnnotation("");
        service.methodWithStringArgs("Тузик");
        service.methodWithIntegerArgs(5);
        service.methodWithCollectionArgs(List.of());
    }
}