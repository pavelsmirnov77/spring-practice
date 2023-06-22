package ru.sber.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sber.config.ProjectConfig;
import ru.sber.services.ArgumentsService;

import java.util.List;

/**
 * Файл для тестирования работы аннотации
 */
public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        var service = context.getBean(ArgumentsService.class);

        List<Integer> list = List.of();

        service.methodWithCollectionArgs(List.of(1, 2, 3));
        service.methodWithoutAnnotation("");
        service.methodWithStringArgs("Hello world!");
        service.methodWithIntegerArgs(100);
        service.methodWithCollectionArgs(list);
    }
}