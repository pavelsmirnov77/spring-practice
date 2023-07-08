package ru.sber.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sber.entities.UserResponse;
import ru.sber.entities.User;
import ru.sber.services.CartService;
import ru.sber.services.UserService;

import java.net.URI;
import java.util.Optional;


@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final CartService cartService;

    @Autowired
    public UserController(UserService userService, CartService cartService) {
        this.userService = userService;
        this.cartService = cartService;
    }

    /**
     * Регистрирует нового пользователя
     *
     * @param user Пользователь
     * @return id зарегистрированного пользователя
     */
    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody User user) {
        long userId = userService.signUp(user);
        log.info("Регистрация пользователя {}", user);
        return ResponseEntity.created(URI.create("/users/" + userId)).build();
    }

    /**
     * Находит пользователя по идентификатору
     *
     * @param id id пользователя
     * @return пользователь с ограниченным количеством полей
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable long id) {

        log.info("Выводим данные о пользователе с id: {}", id);

        Optional<User> user = userService.getUserById(id);

        if (user.isPresent()) {
            UserResponse userResponse = new UserResponse(user.get());
            userResponse.setCart(cartService.getListOfProductsInCart(id));
            return ResponseEntity.ok().body(userResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Удаляет пользователя по id
     *
     * @param id id пользователя
     * @return статус выполнения
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {

        log.info("Удаляем пользователя с id: {}", id);

        boolean isDeleted = userService.deleteUserById(id);

        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping()
    public ResponseEntity<?> getUserByLoginAndPassword(@RequestParam("login") String login,
                                                       @RequestParam("password") String password) {

        log.info("Проверка пользователя");

        Optional<User> checkUser = userService.findByLoginAndPassword(login, password);

        if (checkUser.isPresent()) {
            long id = checkUser.get().getId();
            return ResponseEntity.ok().body(id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
