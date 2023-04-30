package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ManagerException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.manager.UserManager;
import ru.yandex.practicum.filmorate.model.*;


import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserManager userManager = new UserManager();

    @GetMapping
    public List<User> findAll() {
        log.debug("Количество пользователей: " + userManager.getSizeStorage());
        return userManager.getListOfAllUsers();
    }

    @PostMapping
    public User create(@RequestBody User user) throws ValidationException {
        userManager.addUser(user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) throws ManagerException {
        userManager.updateUser(user);
        return user;
    }



}
