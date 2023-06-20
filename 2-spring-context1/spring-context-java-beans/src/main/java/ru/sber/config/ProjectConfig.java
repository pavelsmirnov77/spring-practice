package ru.sber.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sber.models.Cat;
import ru.sber.models.Dog;
import ru.sber.models.Parrot;
import ru.sber.models.Person;

@Configuration
public class ProjectConfig {
    @Bean
    public Parrot firstParrot() {
        var firstParrot = new Parrot();
        firstParrot.setName("Кеша");
        return firstParrot;
    }

    @Bean
    public Parrot secondParrot() {
        var secondParrot = new Parrot();
        secondParrot.setName("Цыпа");
        return secondParrot;
    }

    @Bean
    public Cat cat() {
        var cat = new Cat();
        cat.setName("Тима");
        return cat;
    }

    @Bean
    public Dog dog() {
        var dog = new Dog();
        dog.setName("Бобик");
        return dog;
    }

    @Bean
    public Person person() {
        var person = new Person();
        person.setName("Павел");
        person.setFirstParrot(firstParrot());
        person.setSecondParrot(secondParrot());
        person.setCat(cat());
        person.setDog(dog());
        return person;
    }
}
