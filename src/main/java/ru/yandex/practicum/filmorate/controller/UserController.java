package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.model.*;

import javax.validation.Valid;
import java.util.*;

@Validated
@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private UserService userService = new UserService();

    @GetMapping
    public List<User> findAll() {
        log.debug("Количество пользователей: " + userService.getSizeStorage());
        return userService.getListOfAllUsers();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
            log.warn("Добавляемый пользователь не содержит имени, вместо него подставлен логин.");
        }
        return userService.addUser(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return userService.updateUser(user);
    }



}
