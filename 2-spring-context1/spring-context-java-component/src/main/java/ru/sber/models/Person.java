package ru.sber.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Person {
    private String name = "Павел";

    private final Parrot firstParrot;
    private final Parrot secondParrot;
    private final Cat cat;
    private final Dog dog;

    @Autowired
    public Person(@Qualifier("firstParrot") Parrot firstParrot,@Qualifier("secondParrot") Parrot secondParrot, Cat cat, Dog dog) {
        this.firstParrot = firstParrot;
        this.secondParrot = secondParrot;
        this.cat = cat;
        this.dog = dog;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Parrot getFirstParrot() {
        return firstParrot;
    }

    public Parrot getSecondParrot() {
        return secondParrot;
    }

    public Cat getCat() {
        return cat;
    }

    public Dog getDog() {
        return dog;
    }
}
