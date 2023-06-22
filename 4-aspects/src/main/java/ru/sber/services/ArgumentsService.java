package ru.sber.services;

import org.springframework.stereotype.Service;
import ru.sber.aspects.NotEmpty;

import java.util.List;
import java.util.logging.Logger;

/**
 * Сервис, реализующий методы с аннотацией @NotEmpty
 */
@Service
public class ArgumentsService {

    private Logger logger = Logger.getLogger(ArgumentsService.class.getName());
    @NotEmpty
    public String methodWithStringArgs(String text) {
        logger.info("Текст: " + text);
        return "SUCCESS";
    }

    @NotEmpty
    public String methodWithIntegerArgs(Integer number) {
        logger.info(number.toString());
        return "SUCCESS";
    }

    @NotEmpty
    public String methodWithCollectionArgs(List<Integer> numbers) {
        logger.info("Коллекция: " + numbers.toString());
        return "SUCCESS";
    }

    public String methodWithoutAnnotation(String text) {
        logger.info(text);
        return "SUCCESS";
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}
