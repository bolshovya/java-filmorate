package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.UsersService;
import ru.yandex.practicum.filmorate.model.*;

import javax.validation.Valid;
import java.util.*;

@Validated
@RestController
@Slf4j
@RequestMapping("/users")
public class UsersController {

    private final UsersService usersService;

    @Autowired
    public UsersController() {
        this.usersService = new UsersService();
    }

    @GetMapping
    public List<User> findAll() {
        log.debug("Количество пользователей: " + usersService.getSizeStorage());
        return usersService.getListOfAllUsers();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable int id) {
        log.debug("GET-запрос данных о пользователе ID: " + id);
        return usersService.findById(id);
    }

    @GetMapping("/{id}/friends")
    public List<User> findFriendsById(@PathVariable int id) {
        log.debug("GET-запрос данных о списке друзей пользователя ID: " + id);
        return usersService.getFriendListById(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> findMutualFriend(@PathVariable int id, @PathVariable int otherId) {
        log.debug("GET-запрос общих друзей пользователей ID: " + id + ", ID: " + otherId);
        return usersService.findMutualFriends(id, otherId);
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        if (user.getName() == null | user.getName().equals("")) {
            user.setName(user.getLogin());
            log.warn("Добавляемый пользователь не содержит имени, вместо него подставлен логин.");
        }
        return usersService.addUser(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return usersService.updateUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public List<User> addToFriends(@PathVariable int id, @PathVariable int friendId) {
        return usersService.addToFriends(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public List<User> removeFromFriends(@PathVariable int id, @PathVariable int friendId) {
        return usersService.removeFromFriends(id, friendId);
    }

}
