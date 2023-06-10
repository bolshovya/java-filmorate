package ru.yandex.practicum.filmorate.storage.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UsersStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class UsersDbStorage implements UsersStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public User addUser(User addedUser) {
        jdbcTemplate.update("INSERT INTO users (email, login, name, birthday) VALUES(?, ?, ?, ?)",
                addedUser.getEmail(),
                addedUser.getLogin(),
                addedUser.getName(),
                addedUser.getBirthday());
        return addedUser;
    }

    @Override
    public User updateUser(User updatedUser) {
        jdbcTemplate.update("UPDATE users SET email=?, login=?, name=?, birthday=?, WHERE id=?",
                updatedUser.getEmail(),
                updatedUser.getLogin(),
                updatedUser.getName(),
                updatedUser.getBirthday(),
                updatedUser.getId());
        return updatedUser;
    }

    @Override
    public User removeUserFromStorage(User removedUser) {
        jdbcTemplate.update("DELETE FROM users WHERE id=?", removedUser.getId());
        return removedUser;
    }

    @Override
    public List<User> getListOfAllUsers() {
        return jdbcTemplate.query("SELECT * FROM users", new UserMapper());
    }


    @Override
    public User findById(int id) throws UserNotFoundException {

        // return jdbcTemplate.query("SELECT * FROM users WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(User.class).stream().findAny().orElse(null);

        Optional<User> optUser = jdbcTemplate.query("SELECT * FROM users WHERE id=?",
                new Object[]{id}, new UserMapper())
                .stream().findAny();
        if (optUser.isEmpty()) {
            throw new UserNotFoundException();
        }
        return optUser.get();
        /*
        Optional<Integer> optUserId = Optional.of(updatedUser.getId());
        if (optUserId.isEmpty() || !usersStorage.containsKey(updatedUser.getId())) {
            throw new UserNotFoundException();
        }
        usersStorage.put(updatedUser.getId(), updatedUser);
        return updatedUser;

         */
    }

    public int getSizeStorage() {
        return 0;
    }

    private final class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();

            user.setId(rs.getInt("id"));
            user.setEmail(rs.getString("email"));
            user.setLogin(rs.getString("login"));
            user.setName(rs.getString("name"));
            user.setBirthday(rs.getDate("birthday").toLocalDate());

            return user;
        }
    }
}
