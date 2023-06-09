package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenresStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenresDbStorage implements GenresStorage {

    private final JdbcTemplate jdbcTemplate;

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
    public void addFilmGenre(Film film) {
        log.info("GenresDbStorage: сохранение жанров для фильма: {}", film);
        String sqlQuery = "INSERT INTO filmGenre (film_id, genre_id) VALUES (?, ?)";
        Set<Genre> filmGenres = film.getGenres();
        jdbcTemplate.batchUpdate(sqlQuery, filmGenres, 100, (PreparedStatement ps, Genre genre) -> {
            ps.setInt(1, film.getId());
            ps.setInt(2, genre.getId());
        });
    }

    @Override
    public Optional<Genre> findById(int id) {
        log.info("GenresDbStorage: получение жанра с id: {}", id);
        return jdbcTemplate.query("SELECT * FROM genres WHERE id=?", new GenreMapper(), id).stream().findAny();
    }

    @Override
    public List<Genre> getListOfAllGenre() {
        log.info("GenresDbStorage: получение списка всех жанров");
        String sqlQuery = "SELECT * FROM genres";
        return jdbcTemplate.query(sqlQuery, new GenreMapper());
    }

    @Override
    public void updateGenreForFilm(Film film) {
        log.info("GenresDbStorage: обновление списка жанров для фильма: {}", film);
        String sqlDeleteGenre = "DELETE FROM filmGenre WHERE film_id=?";
        jdbcTemplate.update(sqlDeleteGenre, film.getId());

        if (!film.getGenres().isEmpty() || !(film.getGenres() == null)) {
            String sqlQueryInsertGenre = "INSERT INTO filmGenre (film_id, genre_id) VALUES (?, ?)";
            Set<Genre> filmGenres = film.getGenres();
            jdbcTemplate.batchUpdate(sqlQueryInsertGenre, filmGenres, 100, (PreparedStatement ps, Genre genre) -> {
                ps.setInt(1, film.getId());
                ps.setInt(2, genre.getId());
            });
        }
        film.setGenres(findGenresByFilmID(film.getId()));
    }

    @Override
    public Set<Genre> findGenresByFilmID(int filmId) {
        log.info("GenresDbStorage: получение множества жанров фильма с id: {}", filmId);
        String sqlQuery = "SELECT DISTINCT g.id, g.name FROM FILMGENRE fg JOIN GENRES g ON FG .genre_ID = g.id WHERE fg.film_id=?";
        return jdbcTemplate.query(sqlQuery, new GenreMapper(), filmId).stream().sorted(Comparator.comparingInt(Genre::getId))
                .collect(Collectors.toSet());
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
