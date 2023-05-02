package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ManagerException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private UserController userController;
    private User user1;
    private User user2;

    @BeforeEach
    void beforeEach() {
        userController = new UserController();
        user1 = new User("alexeyi", "Alexey", "alexey@ivanov.ru", "2000-01-01");
        user2 = new User("serega2023", "Sergey", "serega2023@mail.ru", "1990-02-02");
    }

    @Test
    void shouldReturnUserTest() throws ValidationException {
        userController.create(user1);
        assertEquals(1, userController.findAll().size());
        assertEquals(user1, userController.findAll().get(0));
    }

    @Test
    void shouldReturnExceptionTest() {
        User user3 = new User("ivanivi", "Ivan", "ivanivanov.ru", "1995-05-05");
        Exception exception = assertThrows(ValidationException.class, () -> userController.create(user3));
        assertEquals("Email: incorrect date format.", exception.getMessage());
    }

    @Test
    void shouldReturnExceptionYearTest() {
        User user3 = new User("ivanivi", "Ivan", "ivan@ivanov.ru", "2095-05-05");
        Exception exception = assertThrows(ValidationException.class, () -> userController.create(user3));
        assertEquals("Birthday: incorrect date format.", exception.getMessage());
    }

    @Test
    void shouldReturnUserListTest() throws ValidationException {
        assertEquals(0, userController.findAll().size());
        userController.create(user1);
        userController.create(user2);
        assertEquals(List.of(user1, user2), userController.findAll());
    }

    @Test
    void shouldUpdateUserTest() throws ValidationException, ManagerException {
        userController.create(user1);
        user1.setName("Vyacheslav");
        userController.update(user1);
        assertEquals("Vyacheslav", userController.findAll().get(0).getName());
    }

}