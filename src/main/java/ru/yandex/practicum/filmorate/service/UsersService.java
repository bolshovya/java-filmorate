package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.impl.InMemoryUsersStorage;
import ru.yandex.practicum.filmorate.storage.user.UsersStorage;
import ru.yandex.practicum.filmorate.storage.user.impl.UsersDbStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsersService {

    private UsersStorage usersStorage;

    @Autowired
    public UsersService(UsersDbStorage usersDbStorage) {
        this.usersStorage = usersDbStorage;
    }

    public List<User> getListOfAllUsers() {
        return usersStorage.getListOfAllUsers();
    }

    public User findById(int id) {
        return usersStorage.findById(id);
    }

    /*
    public int getSizeStorage() {
        return usersStorage.getSizeStorage();
    }

     */

    public User addUser(User addedUser) {
        return usersStorage.addUser(addedUser);
    }

    public User updateUser(User updatedUser) {
        return usersStorage.updateUser(updatedUser);
    }

    public List<User> getFriendListById(int userId) {
        User searchedUser = usersStorage.findById(userId);
        return searchedUser.getFriends().stream().map(x -> usersStorage.findById(x)).collect(Collectors.toList());
    }

    public List<User> addToFriends(int userId1, int userId2) {  // добавление в друзья
        User user1 = usersStorage.findById(userId1);
        User user2 = usersStorage.findById(userId2);
        user1.addFriend(user2);
        usersStorage.updateUser(user1);
        user2.addFriend(user1);
        usersStorage.updateUser(user2);
        return List.of(user1, user2);
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

    public Set<Integer> getListIdMutualFriends(int userId1, int userId2) { // вывод списка общих друзей
        User user1 = usersStorage.findById(userId1);
        User user2 = usersStorage.findById(userId2);
        Set<Integer> mutualSet = new LinkedHashSet<>(user1.getFriends());
        mutualSet.retainAll(user2.getFriends());
        return mutualSet;
    }

    public List<User> findMutualFriends(int userId1, int userId2) { // вывод списка общих друзей
        User user1 = usersStorage.findById(userId1);
        User user2 = usersStorage.findById(userId2);
        Set<Integer> mutualSet = new LinkedHashSet<>(user1.getFriends());
        mutualSet.retainAll(user2.getFriends());
        return mutualSet.stream().map(x -> usersStorage.findById(x)).collect(Collectors.toList());
    }

}
