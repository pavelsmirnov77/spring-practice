package ru.sber.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sber.config.ProjectConfig;
import ru.sber.models.Person;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);

        Person person = context.getBean(Person.class);

        System.out.println("Имя человека: " + person.getName());
        System.out.println("Первый попугай человека: " + person.getFirstParrot());
        System.out.println("Второй попугай человека: " + person.getSecondParrot());
        System.out.println("Кот человека: " + person.getCat());
        System.out.println("Собака человека: " + person.getDog());

    }
}