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

    public Optional<User> findById(int id) {
        return Optional.of(usersStorage.get(id));
    }

    public List<User> getListOfAllUsers() {
        return new ArrayList<>(usersStorage.values());
    }

    public int getSizeStorage() {
        return usersStorage.size();
    }

    @Override
    public List<User> getFriendListById(int id) {
        return findById(id).get().getFriends().stream().collect(Collectors.toList());
    }

    @Override
    public void addToFriends(int userId1, int userId2) {  // добавление в друзья
        User user1 = findById(userId1).orElseThrow(() -> new UserNotFoundException("Пользователь с id: " + userId1 + " не найден)"));
        User user2 = findById(userId2).orElseThrow(() -> new UserNotFoundException("Пользователь с id: " + userId2 + " не найден)"));
        user1.addFriend(user2);
        updateUser(user1);
        user2.addFriend(user1);
        updateUser(user2);
    }

    @Override
    public List<User> findMutualFriends(int userId1, int userId2) {
        User user1 = findById(userId1).orElseThrow(() -> new UserNotFoundException("Пользователь с id: " + userId1 + " не найден)"));
        User user2 = findById(userId2).orElseThrow(() -> new UserNotFoundException("Пользователь с id: " + userId2 + " не найден)"));
        Set<User> mutualSet = new HashSet<>(user1.getFriends());
        mutualSet.retainAll(user2.getFriends());
        return mutualSet.stream().collect(Collectors.toList());
    }

    @Override
    public void removeFromFriends(int userId1, int userId2) { // удаление из друзей
        User user1 = findById(userId1).orElseThrow(() -> new UserNotFoundException("Пользователь с id: " + userId1 + " не найден)"));
        User user2 = findById(userId2).orElseThrow(() -> new UserNotFoundException("Пользователь с id: " + userId2 + " не найден)"));
        user1.removeFriend(user2);
        updateUser(user1);
        user2.removeFriend(user1);
        updateUser(user2);
    }

}
