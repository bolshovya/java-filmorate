package ru.yandex.practicum.filmorate.storage.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmGenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

@Component
@Slf4j
public class FilmGenreDbStorage implements FilmGenreStorage {

    private final JdbcTemplate jdbcTemplate;


    public FilmGenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFilmGenre(Film film) {
        log.info("FilmGenreDbStorage: сохранение жанров для фильма: {}", film);
        String sqlQuery = "INSERT INTO filmGenre (film_id, genre_id) VALUES (?, ?)";
        Set<Genre> filmGenres = film.getGenres();
        for (Genre genre : filmGenres) {
            jdbcTemplate.update(sqlQuery, film.getId(), genre.getId());
        }
    }


    private final class FilmGenreMapper implements RowMapper<FilmGenre> {

        @Override
        public FilmGenre mapRow(ResultSet rs, int rowNum) throws SQLException {
            FilmGenre filmGenre = new FilmGenre();
            filmGenre.setFilmId(rs.getInt("film_id"));
            filmGenre.setGenreId(rs.getInt("genre_id"));
            return filmGenre;
        }
    }

}
