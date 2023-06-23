package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationUsersException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UsersStorage;
import ru.yandex.practicum.filmorate.storage.dao.UsersDbStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsersService {

    private UsersStorage usersStorage;

    @Autowired
    public UsersService(UsersDbStorage usersDbStorage) {
        this.usersStorage = usersDbStorage;
    }

    public User addUser(User addedUser) {
        return usersStorage.addUser(addedUser);
    }

    public User findById(int id) {
        return usersStorage.findById(id);
    }

    public List<User> getListOfAllUsers() {
        return usersStorage.getListOfAllUsers();
    }

    public User updateUser(User updatedUser) {
        return usersStorage.updateUser(updatedUser);
    }

    public int getSizeStorage() {
        return usersStorage.getSizeStorage();
    }

    public List<User> getFriendListById(int userId) {
        return usersStorage.getFriendListById(userId);
    }

    public void addToFriends(int userId1, int userId2) {  // добавление в друзья
        if (usersStorage.findById(userId1) == null | usersStorage.findById(userId2) == null) {
            throw new UserNotFoundException();
        }
        usersStorage.addToFriends(userId1, userId2);
    }

    public List<User> removeFromFriends(int userId1, int userId2) { // удаление из друзей
        User user1 = usersStorage.findById(userId1);
        User user2 = usersStorage.findById(userId2);
        user1.removeFriend(user2);
        usersStorage.updateUser(user1);
        user2.removeFriend(user1);
        usersStorage.updateUser(user2);
        return List.of(user1, user2);
    }

    public Set<User> getListIdMutualFriends(int userId1, int userId2) { // вывод списка общих друзей
        User user1 = usersStorage.findById(userId1);
        User user2 = usersStorage.findById(userId2);
        Set<User> mutualSet = new HashSet<>(user1.getFriends());
        mutualSet.retainAll(user2.getFriends());
        return mutualSet;
    }

    public List<User> findMutualFriends(int userId1, int userId2) { // вывод списка общих друзей
        User user1 = usersStorage.findById(userId1);
        User user2 = usersStorage.findById(userId2);
        Set<User> mutualSet = new HashSet<>(user1.getFriends());
        mutualSet.retainAll(user2.getFriends());
        return mutualSet.stream().collect(Collectors.toList());
    }

}
