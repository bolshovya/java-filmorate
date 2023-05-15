package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ManagerException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Component
public class InMemoryUsersStorage implements UsersStorage {
    private final Map<Integer, User> usersStorage = new HashMap<>();  // ket - id, value - User
    private static int USERS_COUNT;


    public int setId() {
        return ++USERS_COUNT;
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
        usersStorage.put(user.getId(), user);
        return user;
    }

    public User updateUser(User updatedUser) throws ManagerException {
        Optional<Integer> optUserId = Optional.of(updatedUser.getId());
        if (optUserId.isEmpty() || !usersStorage.containsKey(updatedUser.getId())) {
            throw new ManagerException("Added user not listed.");
        }
        usersStorage.put(updatedUser.getId(), updatedUser);
        return updatedUser;
    }

    public User removeUserFromStorage(User removedUser) {
        usersStorage.remove(removedUser.getId());
        return removedUser;
    }

    public User findById(int id) throws ManagerException {
        if (!usersStorage.containsKey(id)) {
            log.error("ManagerException: user with this id not found");
            throw new ManagerException("User with this id not found.");
        } else {
            return usersStorage.get(id);
        }
    }

    public List<User> getListOfAllUsers() {
        return new ArrayList<>(usersStorage.values());
    }

    public int getSizeStorage() {
        return usersStorage.size();
    }



}
