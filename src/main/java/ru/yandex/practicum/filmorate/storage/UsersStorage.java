package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;


public interface UsersStorage {

    User addUser(User addedUser);

    User findById(int id);

    List<User> getListOfAllUsers();

    User updateUser(User updatedUser);

    User removeUserFromStorage(User removedUser);

    int getSizeStorage();

    List<User> getFriendListById(int id);

    void addToFriends(int userId1, int userId2);
}
