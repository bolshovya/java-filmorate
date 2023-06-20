package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenresStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class GenresDbStorage implements GenresStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenresDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Genre findById(int id) {
        String sqlQuery = "SELECT * FROM genres WHERE id=?";
        return jdbcTemplate.queryForObject(sqlQuery, new GenreMapper());
    }

    @Override
    public List<Genre> getListOfAllGenre() {
        String sqlQuery = "SELECT * FROM genres";
        return jdbcTemplate.query(sqlQuery, new GenreMapper());
    }

    private final class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            Genre genre = new Genre();

            genre.setFilmId(rs.getInt("film_id"));
            genre.setGenre(rs.getString("genre"));

            return genre;
        }
    }
}
