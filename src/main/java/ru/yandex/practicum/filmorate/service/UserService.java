package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserStorage userStorage;

    @Autowired
    public UserService() {
        this.userStorage = new InMemoryUserStorage();
    }

    public List<User> getListOfAllUsers() {
        return userStorage.getListOfAllUsers();
    }

    public User findById(int id) {
        return userStorage.findById(id);
    }

    public int getSizeStorage() {
        return userStorage.getSizeStorage();
    }

    public User addUser(User addedUser) {
        return userStorage.addUser(addedUser);
    }

    public User updateUser(User updatedUser) {
        return userStorage.updateUser(updatedUser);
    }

    public List<User> getFriendListById(int userId) {
        User searchedUser = userStorage.findById(userId);
        return searchedUser.getFriends().stream().map(x -> userStorage.findById(x)).collect(Collectors.toList());
    }

    public List<User> addToFriends(int userId1, int userId2) {  // добавление в друзья
        User user1 = userStorage.findById(userId1);
        User user2 = userStorage.findById(userId2);
        user1.addFriend(user2);
        userStorage.updateUser(user1);
        user2.addFriend(user1);
        userStorage.updateUser(user2);
        return List.of(user1, user2);
    }

    public List<User> removeFromFriends(int userId1, int userId2) { // удаление из друзей
        User user1 = userStorage.findById(userId1);
        User user2 = userStorage.findById(userId2);
        user1.removeFriend(user2);
        userStorage.updateUser(user1);
        user2.removeFriend(user1);
        userStorage.updateUser(user2);
        return List.of(user1, user2);
    }

    public Set<Integer> getListIdMutualFriends(int userId1, int userId2) { // вывод списка общих друзей
        User user1 = userStorage.findById(userId1);
        User user2 = userStorage.findById(userId2);
        Set<Integer> mutualSet = new LinkedHashSet<>(user1.getFriends());
        mutualSet.retainAll(user2.getFriends());
        return mutualSet;
    }

    public List<User> findMutualFriends(int userId1, int userId2) { // вывод списка общих друзей
        User user1 = userStorage.findById(userId1);
        User user2 = userStorage.findById(userId2);
        Set<Integer> mutualSet = new LinkedHashSet<>(user1.getFriends());
        mutualSet.retainAll(user2.getFriends());
        return mutualSet.stream().map(x -> userStorage.findById(x)).collect(Collectors.toList());
    }

}
