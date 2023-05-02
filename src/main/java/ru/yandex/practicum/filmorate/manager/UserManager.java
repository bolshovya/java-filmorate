package ru.yandex.practicum.filmorate.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.practicum.filmorate.exception.ManagerException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

public class UserManager {
    private static final Logger log = LoggerFactory.getLogger(UserManager.class);
    private final Map<Integer, User> userStorage = new HashMap<>();
    private static int idCount;


    public int setId() {
        return ++idCount;
    }

    public int getId() {
        return idCount;
    }

    public User validation(User addedUser) throws ValidationException {
        if (addedUser.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Birthday: incorrect date format.");
        }
        if (!addedUser.getEmail().contains("@") || addedUser.getEmail().isBlank()) {
            throw new ValidationException("Email: incorrect date format.");
        }
        if (addedUser.getLogin().isBlank()) {
            throw new ValidationException("Login: incorrect date format.");
        }
        return addedUser;
    }

    public User addUser(User addedUser) throws ValidationException {
        User user = validation(addedUser);
        user.setId(setId());
        userStorage.put(user.getId(), user);
        return user;
    }

    public User updateUser(User updatedUser) throws ManagerException {
        Optional<Integer> optUserId = Optional.of(updatedUser.getId());
        if (optUserId.isEmpty() || !userStorage.containsKey(updatedUser.getId())) {
            throw new ManagerException("Added film not listed.");
        }
        userStorage.put(updatedUser.getId(), updatedUser);
        return updatedUser;
    }

    public User removeUserFromStorage(User removedUser) {
        userStorage.remove(removedUser.getId());
        return removedUser;
    }

    public User findById(int id) throws ManagerException {
        if (!userStorage.containsKey(id)) {
            log.error("ManagerException: film with this id not found");
            throw new ManagerException("Film with this id not found.");
        } else {
            return userStorage.get(id);
        }
    }

    public List<User> getListOfAllUsers() {
        return new ArrayList<>(userStorage.values());
    }

    public int getSizeStorage() {
        return userStorage.size();
    }

}
