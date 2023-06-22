package ru.sber.models;

/**
 * Класс сущености животного
 */
public class Animals {
    private String type;
    private String name;
    private Integer age;

    public Animals(String type, String name, Integer age) {
        this.type = type;
        this.name = name;
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Вид: " + getType() + ", имя: " + getName() + ", возраст: " + getAge();
    }
}
