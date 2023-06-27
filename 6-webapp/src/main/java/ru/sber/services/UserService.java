package ru.sber.services;

import org.springframework.stereotype.Service;
import ru.sber.entities.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для поиска пользователя по электронной почте и получения списка всех пользователей
 */
@Service
public class UserService implements UserInterfaceService {
    private List<User> users = new ArrayList<>(List.of(
            new User(1, "Павел", "89473829384", "pavel@yandex.ru", "123"),
            new User(2, "Валерий", "89324356653", "valeriy@yandex.ru", "123")
    ));

    /**
     * Получает список найденных пользователей
     * @param emailUser электронная почта пользователя
     * @return список найденных пользователей
     */
    @Override
    public List<User> searchUser(String emailUser) {
        List<User> searchResults = new ArrayList<>();
        for (User user : users) {
            if (user.email().contains(emailUser)) {
                searchResults.add(user);
            }
        }
        return searchResults;
    }

    /**
     * Получает список всех пользователей
     * @return список всех пользователей
     */
    public List<User> getAllUsers() {
        return users;
    }
}
