package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public List<User> findAll() {
        log.debug("Количество пользователей: " + users.size());
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User create(@RequestBody User user) {
        try {
            if (!user.getEmail().contains("@") || user.getEmail().isEmpty() || user.getEmail()==null) {
                throw new ValidationException("Email: incorrect date format.");
            }
            if (user.getLogin().contains(" ") || user.getLogin().isEmpty() || user.getLogin()==null) {
                throw new ValidationException("Login: incorrect date format.");

            }
            if (user.getName().isEmpty() || user.getName()==null) {
                user.setName(user.getLogin());
            }
            if (user.getBirthday().isAfter(LocalDate.now())) {
                throw new ValidationException("Birthday: incorrect date format.");
            }
            users.put(user.getId(), user);
        } catch (ValidationException e) {
            e.getMessage();
        }
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) {
        try {
            if (!user.getEmail().contains("@") || user.getEmail().isEmpty() || user.getEmail()==null) {
                throw new ValidationException("Email: incorrect date format.");
            }
            if (user.getLogin().contains(" ") || user.getLogin().isEmpty() || user.getLogin()==null) {
                throw new ValidationException("Login: incorrect date format.");

            }
            if (user.getName().isEmpty() || user.getName()==null) {
                user.setName(user.getLogin());
            }
            if (user.getBirthday().isAfter(LocalDate.now())) {
                throw new ValidationException("Birthday: incorrect date format.");
            }
            if (!users.containsKey(user.getId())) {
                throw new ValidationException("Added user doesn't contain in the list.");
            }
            if (users.containsKey(user.getId())) {
                users.put(user.getId(), user);
            }
        } catch (ValidationException e) {
            e.getMessage();
        }
        return user;
    }



}
