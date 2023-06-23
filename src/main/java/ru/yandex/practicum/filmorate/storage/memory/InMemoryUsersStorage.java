package ru.yandex.practicum.filmorate.storage.memory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationUsersException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UsersStorage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class InMemoryUsersStorage implements UsersStorage {
    private final Map<Integer, User> usersStorage = new HashMap<>();  // ket - id, value - User
    private static int USERS_COUNT;


    public int setId() {
        return ++USERS_COUNT;
    }

    public User validation(User addedUser) {
        if (addedUser.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationUsersException("Birthday: incorrect date format.");
        }
        if (!addedUser.getEmail().contains("@") || addedUser.getEmail().isBlank()) {
            throw new ValidationUsersException("Email: incorrect date format.");
        }
        if (addedUser.getLogin().isBlank()) {
            throw new ValidationUsersException("Login: incorrect date format.");
        }
        return addedUser;
    }

    public User addUser(User addedUser) throws ValidationUsersException {
        User user = validation(addedUser);
        user.setId(setId());
        usersStorage.put(user.getId(), user);
        return user;
    }

    public User updateUser(User updatedUser) throws UserNotFoundException {
        Optional<Integer> optUserId = Optional.of(updatedUser.getId());
        if (optUserId.isEmpty() || !usersStorage.containsKey(updatedUser.getId())) {
            throw new UserNotFoundException();
        }
        usersStorage.put(updatedUser.getId(), updatedUser);
        return updatedUser;
    }

    public User removeUserFromStorage(User removedUser) {
        usersStorage.remove(removedUser.getId());
        return removedUser;
    }

    public User findById(int id) throws UserNotFoundException {
        if (!usersStorage.containsKey(id)) {
            log.error("UserNotFoundException: user with this id not found");
            throw new UserNotFoundException();
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

    @Override
    public List<User> getFriendListById(int id) {
        return findById(id).getFriends().stream().collect(Collectors.toList());
    }

    @Override
    public void addToFriends(int userId1, int userId2) {  // добавление в друзья
        User user1 = findById(userId1);
        User user2 = findById(userId2);
        user1.addFriend(user2);
        updateUser(user1);
        user2.addFriend(user1);
        updateUser(user2);
        // return List.of(user1, user2);
    }


}
