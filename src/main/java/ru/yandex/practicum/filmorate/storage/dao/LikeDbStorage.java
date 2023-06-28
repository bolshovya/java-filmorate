package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.storage.LikeStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LikeDbStorage implements LikeStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addLike(int filmId, int userId) {
        log.info("LikeDbStorage: добавление лайка фильму с id: {} от пользователя с id: {}", filmId, userId);
        String sqlQuery = "INSERT INTO likes (film_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public Optional<Like> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Like> getListOfAllLikes() {
        return null;
    }

    @Override
    public Set<Integer> findUserIdByFilmId(int filmId) {
        log.info("LikeDbStorage: получение userId по filmId: {}", filmId);
        String sqlQuery = "SELECT * FROM likes WHERE film_id=?";
        return jdbcTemplate.query(sqlQuery, new LikeMapper(), filmId)
                .stream().map(x -> x.getUserId()).collect(Collectors.toSet());
    }

    @Override
    public void removeLike(int filmId, int userId) {
        log.info("FilmsDbStorage: удаление ллайка фульма с id: {} от пользователя с id: {}", filmId, userId);

        String sqlQuery = "DELETE FROM likes WHERE film_id=? AND user_id=?";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }


    private final class LikeMapper implements RowMapper<Like> {

        @Override
        public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
            Like like = new Like();

            like.setFilmId(rs.getInt("film_id"));
            like.setUserId(rs.getInt("user_id"));

            return like;
        }
    }
}
