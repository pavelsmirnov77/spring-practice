import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.sber.aspects.NotEmptyAspect;
import ru.sber.config.ProjectConfig;
import ru.sber.services.ArgumentsService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ProjectConfig.class})
public class AppTests {
    private static final String NOT_EMPTY_STRING = "Hello world!";
    private static final String EMPTY_STRING = "";
    private static final List<Integer> NOT_EMPTY_COLLECTION = List.of(1, 2, 3);
    private static final List<Integer> EMPTY_COLLECTION = List.of();
    private static final Integer NOT_NULL_NUMBER = 100;
    private static final Integer NULL_NUMBER = null;
    private Logger serviceLogger;
    private Logger notEmptyAspectLogger;

    @Autowired
    private NotEmptyAspect notEmptyAspect;

    @Autowired
    private ArgumentsService argumentsService;

    @BeforeEach
    public void before() {
        this.notEmptyAspectLogger = mock(Logger.class);
        notEmptyAspect.setLogger(notEmptyAspectLogger);

        this.serviceLogger = mock(Logger.class);
        argumentsService.setLogger(serviceLogger);
    }

    @Test
    public void testMethodWithStringArgs() {

        argumentsService.methodWithStringArgs(NOT_EMPTY_STRING);
        verify(serviceLogger).info("Текст: " + NOT_EMPTY_STRING);

        assertThrows(IllegalArgumentException.class, () -> {
            argumentsService.methodWithStringArgs(EMPTY_STRING);
            verify(notEmptyAspectLogger).log(Level.WARNING, "Ошибка: 1-й строчный аргумент является пустым");
        });
    }

    @Test
    public void testMethodWithIntegerArgs() {

        argumentsService.methodWithIntegerArgs(NOT_NULL_NUMBER);
        verify(serviceLogger).info(NOT_NULL_NUMBER.toString());

        assertThrows(IllegalArgumentException.class, () -> {
            argumentsService.methodWithIntegerArgs(NULL_NUMBER);
            verify(notEmptyAspectLogger).log(Level.WARNING, "Ошибка: 1-й аргумент является null");
        });
    }

    @Test
    public void testMethodWithCollectionArgs() {
        argumentsService.methodWithCollectionArgs(NOT_EMPTY_COLLECTION);
        verify(serviceLogger).info("Коллекция: " + NOT_EMPTY_COLLECTION);

        assertThrows(IllegalArgumentException.class, () -> {
            argumentsService.methodWithCollectionArgs(EMPTY_COLLECTION);
            verify(notEmptyAspectLogger).log(Level.WARNING, "Ошибка: 1-й строчный аргумент является пустым");
        });
    }

    @Test
    public void testMethodWithoutAnnotation() {
        argumentsService.methodWithoutAnnotation(EMPTY_STRING);
        verify(serviceLogger).info(EMPTY_STRING);
    }
}
