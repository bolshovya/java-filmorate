package ru.yandex.practicum.filmorate.storage.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenresStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
@Slf4j
public class GenresDbStorage implements GenresStorage {

    private final JdbcTemplate jdbcTemplate;

    private final String SQL_QUERY_GENRE_BY_ID = "SELECT * FROM genres WHERE id=?";

    @Autowired
    public GenresDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Genre addGenre(Genre genre) {
        log.info("GenresDbStorage: сохранение жанра: {}", genre);
        String sqlQuery = "INSERT INTO genres (genre) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement stmt = con.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, genre.getName());
            return stmt;
        }, keyHolder);

        int genreKey = keyHolder.getKey().intValue();

        return jdbcTemplate.queryForObject(sqlQuery, new GenreMapper(), new Object[]{genreKey});
    }

    @Override
    public Optional<Genre> findById(int id) {
        log.info("GenresDbStorage: получение жанра с id: {}", id);
        return jdbcTemplate.query(SQL_QUERY_GENRE_BY_ID, new GenreMapper(), id).stream().findAny();
    }

    @Override
    public List<Genre> getListOfAllGenre() {
        log.info("GenresDbStorage: получение списка всех жанров");
        String sqlQuery = "SELECT * FROM genres";
        return jdbcTemplate.query(sqlQuery, new GenreMapper());
    }

    @Override
    public Set<Genre> findGenresByFilmID(int filmId) {
        log.info("GenresDbStorage: получение множества жанров фильма с id: {}", filmId);
        String sqlQuery = "SELECT DISTINCT g.id, g.name FROM FILMGENRE fg JOIN GENRES g ON FG .genre_ID = g.id WHERE fg.film_id=?";
        return new TreeSet<>(jdbcTemplate.query(sqlQuery, new GenreMapper(), filmId));
    }



    private final class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            Genre genre = new Genre();

            genre.setId(rs.getInt("id"));
            genre.setName(rs.getString("name"));

            return genre;
        }
    }
}
