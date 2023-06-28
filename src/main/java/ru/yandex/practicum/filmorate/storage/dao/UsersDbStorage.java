package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UsersStorage;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.Optional;



@Component
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UsersDbStorage implements UsersStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public User addUser(User user) {
        log.info("UsersDbStorage: сохранение пользователя: {}", user);
        String sqlQuery = "INSERT INTO users (email, login, name, birthday) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement stmt = con.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getName());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));
            return stmt;
        }, keyHolder);

        int userKey = keyHolder.getKey().intValue();

        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id=?", new UserMapper(), new Object[]{userKey});
    }

    @Override
    public Optional<User> findById(int id) {
        log.info("UsersDbStorage: получение пользователя с id: {}", id);
        return jdbcTemplate.query("SELECT * FROM users WHERE id=?", new UserMapper(), id).stream().findAny();
    }


    public List<User> getFriendListById(int id) {
        log.info("UsersDbStorage: получение списка всех друзей пользователей с id: {}", id);
        String sqlQuery = "SELECT * FROM users WHERE id IN (SELECT friend_id FROM friendships WHERE user_id=?)";
        return jdbcTemplate.query(sqlQuery, new UserMapper(), id);
    }

    @Override
    public void addToFriends(int userId1, int userId2) {
        log.info("UsersDbStorage: добавление дружбы между пользователеми с id: {}, {}", userId1, userId2);
        String sqlQuery = "INSERT INTO friendships (user_id, friend_id) VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery, userId1, userId2);
    }

    @Override
    public List<User> getListOfAllUsers() {
        log.info("UsersDbStorage: получение списка всех друзей");
        String sqlQuery = "SELECT * FROM users";
        log.info("UsersDbStorage: получение списка всех ");
        return jdbcTemplate.query(sqlQuery, new UserMapper());
    }

    @Override
    public User updateUser(User updatedUser) {
        log.info("UsersDbStorage: обновление данных пользователя: {}", updatedUser);
        String sqlQuery = "UPDATE users SET email=?, login=?, name=?, birthday=? WHERE id=?";
        int valid = jdbcTemplate.update(sqlQuery,
                updatedUser.getEmail(),
                updatedUser.getLogin(),
                updatedUser.getName(),
                updatedUser.getBirthday(),
                updatedUser.getId());

        if (valid == 0) {
            throw new UserNotFoundException();
        } else {
            return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id=?", new UserMapper(), updatedUser.getId());
        }
    }

    @Override
    public void removeUserFromStorage(User removedUser) {
        jdbcTemplate.update("DELETE FROM users WHERE id=?", removedUser.getId());
    }

    @Override
    public List<User> findMutualFriends(int userId1, int userId2) {
        log.info("UsersDbStorage: получение общий друзей у пользователей с id: {},{}", userId1, userId2);
        String sqlQuery = "SELECT * FROM users WHERE id IN " +
                "(SELECT f.FRIEND_ID  FROM FRIENDSHIPS f " +
                "JOIN FRIENDSHIPS f2 ON f.FRIEND_ID = f2.FRIEND_ID AND f.USER_ID =? AND f2.USER_ID =?)";
        return jdbcTemplate.query(sqlQuery, new UserMapper(), userId1, userId2);
    }

    @Override
    public void removeFromFriends(int userId, int friendId) {
        log.info("UsersDbStorage: удаление у пользователя с id: {} друга с id {}", userId, friendId);
        String sqlQuery = "DELETE FROM friendships WHERE user_id=? AND friend_id=?";
        jdbcTemplate.update(sqlQuery, userId, friendId);
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

    private final class FriendshipMapper implements RowMapper<Friendship> {

        @Override
        public Friendship mapRow(ResultSet rs, int rowNum) throws SQLException {
            Friendship friendship = new Friendship();

            friendship.setUserId(rs.getInt("user_id"));
            friendship.setFriendId(rs.getInt("friend_id"));
            friendship.setConfirmation(rs.getBoolean("confirmation"));

            return friendship;
        }
    }
}
