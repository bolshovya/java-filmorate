package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UsersController {

    private final UsersService usersService;


    @GetMapping
    public List<User> findAll() {
        log.info("GET: получение списка всех пользователей");
        return usersService.getListOfAllUsers();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable int id) {
        log.info("GET: получение данных пользователя с id: {}", id);
        return usersService.findById(id);
    }

    @GetMapping("/{id}/friends")
    public List<User> findFriendsById(@PathVariable int id) {
        log.info("GET: получение списка друзей пользователя с id: {}", id);
        return usersService.getFriendListById(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> findMutualFriend(@PathVariable int id, @PathVariable int otherId) {
        log.info("GEТ: получение общих друзей пользователей id: {}, {}", id, otherId);
        return usersService.findMutualFriends(id, otherId);
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("POST: сохранение пользователя {}", user);
        if (user.getName() == null || user.getName().equals("")) {
            user.setName(user.getLogin());
            log.warn("POST: добавляемый пользователь не содержит имени, вместо него подставлен логин.");
        }
        return usersService.addUser(user);
    }


    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("PUT: обновление данных пользователя {}", user);
        return usersService.updateUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addToFriends(@Valid @PathVariable int id, @PathVariable int friendId) {
        log.info("PUT: добавление дружбы между пользователеми с id: {}, {}", id, friendId);
        usersService.addToFriends(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFromFriends(@PathVariable int id, @PathVariable int friendId) {
        log.info("DELETE: удаление у пользователя с id: {} друга с id {}", id, friendId);
        usersService.removeFromFriends(id, friendId);
    }

}
