package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    public User addUser(User addedUser);

    public User updateUser(User updatedUser);

    public User removeUserFromStorage(User removedUser);

    public List<User> getListOfAllUsers();

    public int getSizeStorage();

    public User findById(int id);

}
