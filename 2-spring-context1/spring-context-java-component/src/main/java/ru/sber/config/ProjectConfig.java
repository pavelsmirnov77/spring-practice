package ru.sber.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.sber.models.Parrot;

@Configuration
@ComponentScan(basePackages = "ru.sber.models")
public class ProjectConfig {
    @Bean
    public Parrot firstParrot() {
        Parrot firstParrot = new Parrot();
        firstParrot.setName("Кеша");
        return firstParrot;
    }

    @Bean
    public Parrot secondParrot() {
        Parrot secondParrot = new Parrot();
        secondParrot.setName("Цыпа");
        return secondParrot;
    }
}
