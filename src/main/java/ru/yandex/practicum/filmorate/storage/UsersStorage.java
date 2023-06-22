package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;


public interface UsersStorage {

    public User addUser(User addedUser);

    public User findById(int id);

    public List<User> getListOfAllUsers();

    public User updateUser(User updatedUser);

    public User removeUserFromStorage(User removedUser);

    public int getSizeStorage();

    public List<User> getFriendListById(int id);
}
