package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmGenreStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class FilmGenreDbStorage implements FilmGenreStorage {

    private final JdbcTemplate jdbcTemplate;


    @Override
    public void addFilmGenre(Film film) {
        log.info("FilmGenreDbStorage: сохранение жанров для фильма: {}", film);
        String sqlQuery = "INSERT INTO filmGenre (film_id, genre_id) VALUES (?, ?)";
        Set<Genre> filmGenres = film.getGenres();
        jdbcTemplate.batchUpdate(sqlQuery, filmGenres, 100, (PreparedStatement ps, Genre genre) -> {
            ps.setInt(1, film.getId());
            ps.setInt(2, genre.getId());
        });
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
