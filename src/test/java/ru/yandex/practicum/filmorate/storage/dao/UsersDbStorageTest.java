package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Sql(scripts = {"/schema.sql", "/usersDbStorageTest.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UsersDbStorageTest {

    private final UsersDbStorage usersDbStorage;

    @Test
    void addUser() {
        User user = new User("555@mail.ru", "555log", "555", LocalDate.of(2001,6,6));
        usersDbStorage.addUser(user);
        assertThat(usersDbStorage.findById(3).get()).hasFieldOrPropertyWithValue("login", "555log");
    }

    @Test
    void findById() {
        assertThat(usersDbStorage.findById(1).get())
                .hasFieldOrPropertyWithValue("email", "ivanivanov@mail.ru")
                .hasFieldOrPropertyWithValue("login", "iivan")
                .hasFieldOrPropertyWithValue("name", "Ivan")
                .hasFieldOrPropertyWithValue("birthday", LocalDate.of(2000, 1, 1));
    }

    @Test
    void updateUser() {
        User user = usersDbStorage.findById(1).get();
        user.setName("Vasiliy");
        usersDbStorage.updateUser(user);
        assertThat(usersDbStorage.findById(1).get()).hasFieldOrPropertyWithValue("name", "Vasiliy");
    }

    @Test
    void removeUserFromStorage() {
        assertEquals(2, usersDbStorage.getListOfAllUsers().size());
        User user = usersDbStorage.findById(1).get();
        usersDbStorage.removeUserFromStorage(user);
        assertEquals(1, usersDbStorage.getListOfAllUsers().size());
    }

    @Test
    void getFriendListById() {
        assertEquals(1, usersDbStorage.getFriendListById(1).size());
    }

    @Test
    void getListOfAllUsers() {
        assertEquals(2, usersDbStorage.getListOfAllUsers().size());
    }

    @Test
    void addToFriends() {
        assertEquals(1, usersDbStorage.getFriendListById(1).size());
        User user3 = new User("mail3@mail.ru", "mail3", "Mail3", LocalDate.of(1999,9,9));
        usersDbStorage.addUser(user3);
        usersDbStorage.addToFriends(1,3);
        assertEquals(2, usersDbStorage.getFriendListById(1).size());
    }
}