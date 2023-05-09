package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.model.*;


import javax.validation.Valid;
import java.util.*;

@Validated
@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserStorage userManager = new InMemoryUserStorage();

    @GetMapping
    public List<User> findAll() {
        log.debug("Количество пользователей: " + userManager.getSizeStorage());
        return userManager.getListOfAllUsers();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
            log.warn("Добавляемый пользователь не содержит имени, вместо него подставлен логин.");
        }
        return userManager.addUser(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return userManager.updateUser(user);
    }



}
