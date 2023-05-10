package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Service
public class UserService {

    private UserStorage userStorage = new InMemoryUserStorage();

    public List<User> getListOfAllUsers() {
        return userStorage.getListOfAllUsers();
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

    /*
    добавление в друзья
     */
    public void addToFriends(User user1, User user2) {
        user1.addFriend(user2);
        user2.addFriend(user1);
    }

    public void addToFriends(int userId1, int userId2) {
        User user1 = userStorage.findById(userId1);
        User user2 = userStorage.findById(userId2);
        user1.addFriend(user2);
        user2.addFriend(user1);
    }

    /*
    удаление из друзей
     */
    public void removeFromFriends(User user1, User user2) {
        user1.removeFriend(user2);
        user2.removeFriend(user1);
    }

    public void removeFromFriends(int userId1, int userId2) {
        User user1 = userStorage.findById(userId1);
        User user2 = userStorage.findById(userId2);
        user1.removeFriend(user2);
        user2.removeFriend(user1);
    }

    /*
    вывод списка общих друзей
     */
    public Set<Integer> getListIdMutualFriends(User user1, User user2) {
        Set<Integer> mutualSet = new LinkedHashSet<>(user1.getFriends());
        mutualSet.retainAll(user2.getFriends());
        return mutualSet;
    }

    public Set<Integer> getListIdMutualFriends(int userId1, int userId2) {
        User user1 = userStorage.findById(userId1);
        User user2 = userStorage.findById(userId2);
        Set<Integer> mutualSet = new LinkedHashSet<>(user1.getFriends());
        mutualSet.retainAll(user2.getFriends());
        return mutualSet;
    }

}
