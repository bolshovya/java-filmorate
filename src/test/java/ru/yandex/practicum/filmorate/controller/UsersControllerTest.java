package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationUsersException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsersControllerTest {

    private UsersController usersController;
    private User user1;
    private User user2;

    @BeforeEach
    void beforeEach() {
        usersController = new UsersController();
        user1 = new User("alexeyi", "Alexey", "alexey@ivanov.ru", "2000-01-01");
        user2 = new User("serega2023", "Sergey", "serega2023@mail.ru", "1990-02-02");
    }

    @Test
    void shouldReturnUserTest() {
        usersController.create(user1);
        assertEquals(1, usersController.findAll().size());
        assertEquals(user1, usersController.findAll().get(0));
    }

    @Test
    void shouldReturnExceptionTest() {
        User user3 = new User("ivanivi", "Ivan", "ivanivanov.ru", "1995-05-05");
        Exception exception = assertThrows(ValidationUsersException.class, () -> usersController.create(user3));
        assertEquals("Email: incorrect date format.", exception.getMessage());
    }

    @Test
    void shouldReturnExceptionYearTest() {
        User user3 = new User("ivanivi", "Ivan", "ivan@ivanov.ru", "2095-05-05");
        Exception exception = assertThrows(ValidationUsersException.class, () -> usersController.create(user3));
        assertEquals("Birthday: incorrect date format.", exception.getMessage());

    }

    @Test
    void shouldReturnUserInsteadWithNameTest() {
        User user3 = new User("ivanivi",null, "ivan@ivanov.ru", "1995-05-05");
        usersController.create(user3);
        User user4 = new User("ivanivi","ivanivi", "ivan@ivanov.ru", "1995-05-05");
        user4.setId(user3.getId());
        assertEquals(user4, user3);
        assertEquals("ivanivi", user3.getName());
    }

    @Test
    void shouldReturnUserListTest() {
        assertEquals(0, usersController.findAll().size());
        usersController.create(user1);
        usersController.create(user2);
        assertEquals(List.of(user1, user2), usersController.findAll());
    }

    @Test
    void shouldUpdateUserTest() {
        usersController.create(user1);
        user1.setName("Vyacheslav");
        usersController.update(user1);
        assertEquals("Vyacheslav", usersController.findAll().get(0).getName());
    }

    @Test
    void shouldReturnFriendListByIdTest() {
        usersController.create(user1);
        usersController.create(user2);
        assertEquals(0, usersController.findFriendsById(user1.getId()).size());
        usersController.addToFriends(user1.getId(), user2.getId());
        assertEquals(1, usersController.findFriendsById(user1.getId()).size());
        assertEquals(List.of(user2), usersController.findFriendsById(user1.getId()));
    }

}