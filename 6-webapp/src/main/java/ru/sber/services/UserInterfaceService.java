package ru.sber.services;

import ru.sber.entities.User;

import java.util.List;

public interface UserInterfaceService {
    List<User> searchUser(String userName);
}
