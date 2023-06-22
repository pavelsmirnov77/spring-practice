import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.sber.aspects.NotEmptyAspect;
import ru.sber.config.ProjectConfig;
import ru.sber.models.Animals;
import ru.sber.services.ArgumentsService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ProjectConfig.class})
public class AppTests {
    private static final String NOT_EMPTY_STRING = "Бобик";
    private static final String EMPTY_STRING = "";
    private static final List<Animals> NOT_EMPTY_COLLECTION = List.of(
            new Animals("Собака", "Бобик", 5)
    );
    private static final List<Animals> EMPTY_COLLECTION = List.of();
    private static final Integer NOT_NULL_NUMBER = 5;
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
        verify(serviceLogger).info("Найдено животное: вид: Собака, имя: Бобик, возраст: 5");

        assertThrows(IllegalArgumentException.class, () -> {
            argumentsService.methodWithStringArgs(EMPTY_STRING);
            verify(notEmptyAspectLogger).log(Level.WARNING, "Ошибка: 1-й строчный аргумент является пустым");
        });
    }

    @Test
    public void testMethodWithIntegerArgs() {

        argumentsService.methodWithIntegerArgs(NOT_NULL_NUMBER);
        verify(serviceLogger).info("Найдено животное: вид: Собака, имя: Бобик, возраст: 5");

        assertThrows(IllegalArgumentException.class, () -> {
            argumentsService.methodWithIntegerArgs(NULL_NUMBER);
            verify(notEmptyAspectLogger).log(Level.WARNING, "Ошибка: 1-й аргумент является null");
        });
    }

    @Test
    public void testMethodWithCollectionArgs() {
        argumentsService.methodWithCollectionArgs(NOT_EMPTY_COLLECTION);
        verify(serviceLogger).info("Список животных: " + NOT_EMPTY_COLLECTION);

        assertThrows(IllegalArgumentException.class, () -> {
            argumentsService.methodWithCollectionArgs(EMPTY_COLLECTION);
            verify(notEmptyAspectLogger).log(Level.WARNING, "Ошибка: 1-й аргумент-список является пустым");
        });
    }

    @Test
    public void testMethodWithoutAnnotation() {
        argumentsService.methodWithoutAnnotation(EMPTY_STRING);
        verify(serviceLogger).info("Животные не найдены");
    }
}
