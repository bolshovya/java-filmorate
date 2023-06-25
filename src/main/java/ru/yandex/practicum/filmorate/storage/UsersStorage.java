package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;


public interface UsersStorage {

    User addUser(User addedUser);

    Optional<User> findById(int id);

    List<User> getListOfAllUsers();

    User updateUser(User updatedUser);

    User removeUserFromStorage(User removedUser);

    int getSizeStorage();

    List<User> getFriendListById(int id);

    void addToFriends(int userId1, int userId2);

    List<User> findMutualFriends(int userId1, int userId2);

    void removeFromFriends(int userId1, int userId2);
}
