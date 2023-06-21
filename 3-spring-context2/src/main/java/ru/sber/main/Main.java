package ru.sber.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sber.config.ProjectConfig;

/**
 * Исполняемый класс для тестирования
 * Здесь создается контекст приложения, получается экземпляр бина класса App и вызывается метод run для тестирования
 */
public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        var app = context.getBean(App.class);

        app.run();
    }
}
