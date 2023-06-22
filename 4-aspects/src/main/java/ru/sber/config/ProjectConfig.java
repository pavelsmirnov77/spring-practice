package ru.sber.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Класс конфигуратор
 */
@Configuration
@ComponentScan(basePackages = {"ru.sber.aspects", "ru.sber.services"})
@EnableAspectJAutoProxy
public class ProjectConfig {
}
