package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UsersStorage;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class UsersDbStorage implements UsersStorage {

    private final JdbcTemplate jdbcTemplate;
    private final static String SQL_QUERY_USER_BY_ID = "SELECT * FROM users WHERE id=?";

    @Autowired
    public UsersDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
    @Override
    public User addUser(User addedUser) {
        jdbcTemplate.update("INSERT INTO users (email, login, name, birthday) VALUES(?, ?, ?, ?)",
                addedUser.getEmail(),
                addedUser.getLogin(),
                addedUser.getName(),
                addedUser.getBirthday());
        return addedUser;
    }
     */

    // create
    @Override
    public User addUser(User user) {
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

        return jdbcTemplate.queryForObject(SQL_QUERY_USER_BY_ID, new UserMapper(), new Object[]{userKey});
    }

    // read
    @Override
    public User findById(int id) throws UserNotFoundException {
        String sqlQuery = "SELECT * FROM users WHERE id=?";
            if (jdbcTemplate.queryForObject(
                "select count(*) from users where id = ?", Integer.class, id) == 0) {
                throw new UserNotFoundException();
            }
        return jdbcTemplate.queryForObject(sqlQuery, new UserMapper(), new Object[] {id});
    }

    @Override
    public List<User> getListOfAllUsers() {
        String sqlQuery = "SELECT * FROM users";
        // return jdbcTemplate.queryForList("SELECT * FROM users", User.class).stream().collect(Collectors.toList());
        return jdbcTemplate.query(sqlQuery, new UserMapper());
    }

    // update
    @Override
    public User updateUser(User updatedUser) {
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
            return jdbcTemplate.queryForObject(SQL_QUERY_USER_BY_ID, new UserMapper(), new Object[]{updatedUser.getId()});
        }
        // return jdbcTemplate.queryForObject(SQL_QUERY_USER_BY_ID, new UserMapper(), new Object[]{updatedUser.getId()});
    }

    // delete
    @Override
    public User removeUserFromStorage(User removedUser) {
        jdbcTemplate.update("DELETE FROM users WHERE id=?", removedUser.getId());

        return jdbcTemplate.queryForObject(SQL_QUERY_USER_BY_ID, new UserMapper(), new Object[]{removedUser.getId()});
    }


    @Override
    public int getSizeStorage() {
        String sqlQuery = "SELECT COUNT(id) FROM users";

        return jdbcTemplate.queryForObject(sqlQuery, Integer.class);
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



    /*
    private class UserMapper implements RowMapper<Optional<User>> {

        @Override
        public Optional<User> mapRow(ResultSet rs, int rowNum) throws SQLException {
            if (rs.wasNull()) {
                return Optional.empty();
            } else {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setLogin(rs.getString("login"));
                user.setName(rs.getString("name"));
                user.setBirthday(rs.getDate("birthday").toLocalDate());
                return Optional.of(user);
            }
        }
    }

     */
}
