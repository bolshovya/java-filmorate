package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UsersStorage;
import ru.yandex.practicum.filmorate.storage.dao.UsersDbStorage;

import java.util.*;

@Service
@Slf4j
public class UsersService {

    private UsersStorage usersStorage;

    @Autowired
    public UsersService(UsersDbStorage usersDbStorage) {
        this.usersStorage = usersDbStorage;
    }

    public User addUser(User addedUser) {
        log.info("UsersService: сохранение данных пользователя: {}", addedUser);
        return usersStorage.addUser(addedUser);
    }

    public User findById(int id) {
        log.info("UsersService: получение данных пользователя с id: {}", id);
        return usersStorage.findById(id).orElseThrow(() -> new UserNotFoundException("Пользователь с id: " + id + " не найден)"));
    }

    public List<User> getListOfAllUsers() {
        return usersStorage.getListOfAllUsers();
    }

    public User updateUser(User updatedUser) {
        log.info("UsersService: обновление данных пользователя: {}", updatedUser);
        return usersStorage.updateUser(updatedUser);
    }

    public int getSizeStorage() {
        return usersStorage.getSizeStorage();
    }

    public List<User> getFriendListById(int userId) {
        log.info("UsersService: получение списка друзей пользователя с id: {}", userId);
        return usersStorage.getFriendListById(userId);
    }

    public void addToFriends(int userId1, int userId2) {  // добавление в друзья
        log.info("UsersService: добавление дружбы между пользователеми с id: {}, {}", userId1, userId2);
        usersStorage.findById(userId1).orElseThrow(() -> new UserNotFoundException("Пользователь с id: " + userId1 + " не найден)"));
        usersStorage.findById(userId2).orElseThrow(() -> new UserNotFoundException("Пользователь с id: " + userId2 + " не найден)"));
        usersStorage.addToFriends(userId1, userId2);
    }

    public void removeFromFriends(int userId, int friendId) { // удаление из друзей
        log.info("UsersService: удаление у пользователя с id: {} друга с id {}", userId, friendId);
        usersStorage.findById(userId).orElseThrow(() -> new UserNotFoundException("Пользователь с id: " + userId + " не найден)"));
        usersStorage.findById(friendId).orElseThrow(() -> new UserNotFoundException("Пользователь с id: " + friendId + " не найден)"));
        usersStorage.removeFromFriends(userId, friendId);
    }


    public List<User> findMutualFriends(int userId1, int userId2) { // вывод списка общих друзей
        log.info("UsersService: получение списка общих друзей для пользователей с id: {},{}", userId1, userId2);
        usersStorage.findById(userId1).orElseThrow(() -> new UserNotFoundException("Пользователь с id: " + userId1 + " не найден)"));
        usersStorage.findById(userId2).orElseThrow(() -> new UserNotFoundException("Пользователь с id: " + userId2 + " не найден)"));
        return usersStorage.findMutualFriends(userId1, userId2);
    }

}
