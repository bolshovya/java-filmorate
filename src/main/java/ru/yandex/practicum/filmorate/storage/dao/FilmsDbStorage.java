package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.storage.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmsDbStorage implements FilmsStorage {

    private final JdbcTemplate jdbcTemplate;
    private final MpaStorage mpaStorage;
    private final GenresStorage genresStorage;


    // create
    @Override
    public Film addFilm(Film addedFilm) {
        log.info("FilmsDbStorage: сохранение фильма: {}", addedFilm);
        Film film = validation(addedFilm);
        String sqlQueryInsertFilm = "INSERT INTO films (name, description, releaseDate, duration, mpa) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement stmt = con.prepareStatement(sqlQueryInsertFilm, new String[]{"id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);

        int filmKey = keyHolder.getKey().intValue();
        addedFilm.setId(filmKey);
        insertGenres(addedFilm);
        return addedFilm;
    }

    private void insertGenres(Film film) {
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
        film.setGenres(genresStorage.findGenresByFilmID(film.getId()));
    }

    private void insertLikes(Film film) {
        String sqlQuery = "SELECT * FROM likes WHERE film_id=?";
        Set<Integer> likes = jdbcTemplate.query(sqlQuery, new LikeMapper(), film.getId())
                .stream().map(x -> x.getUserId()).collect(Collectors.toSet());
        film.setLikes(likes);
    }

    private Film validation(Film addedFilm) {
        if (addedFilm.getName().isBlank()) {
            throw new ValidationFilmsException("Added film doesn't contains name.");
        }
        if (addedFilm.getDescription().toCharArray().length > 200) {
            throw new ValidationFilmsException("The description of the added film contains more than 200 chars.");
        }
        if (addedFilm.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            throw new ValidationFilmsException("Release date not earlier than 28 December 1895.");
        }
        if (addedFilm.getDuration() <= 0) {
            throw new ValidationFilmsException("Duration can't be negative or zero.");
        }
        return addedFilm;
    }

    @Override
    public Optional<Film> findById(int id) {
        log.info("FilmsDbStorage: получение фильма с id: {}", id);
        String sqlQuery = "SELECT f.id , f.name , f.description , f.releasedate , f.duration , f.mpa AS mpa_id , m.name  AS mpa_name\n" +
                "FROM films f JOIN mpa m ON f.mpa = m.id WHERE f.id=?";
        Optional<Film> film = jdbcTemplate.query(sqlQuery, new FilmMpaMapper(), id).stream().findAny();
        film.ifPresent(this::insertLikes);
        return film;
    }

    @Override
    public List<Film> getListOfAllFilms() {
        log.info("FilmsDbStorage: получение списка всех фильмов");
        String sqlQuery = "SELECT f.id , f.name , f.description , f.releasedate , f.duration , f.mpa AS mpa_id , m.name  AS mpa_name\n" +
                "FROM films f JOIN mpa m ON f.mpa = m.id";
        List<Film> films = new ArrayList<>();
        for (Film film : jdbcTemplate.query(sqlQuery, new FilmMpaMapper())) {
            film.setGenres(genresStorage.findGenresByFilmID(film.getId()));
            insertLikes(film);
            films.add(film);
        }
        return films;
    }

    @Override
    public Film updateFilm(Film updatedFilm) {
        log.info("FilmsDbStorage: обновление фильма: {}", updatedFilm);
        String sqlQuery = "UPDATE films SET name=?, description=?, releaseDate=?, duration=?, mpa=? WHERE id=?";

        int valid = jdbcTemplate.update(sqlQuery,
                updatedFilm.getName(),
                updatedFilm.getDescription(),
                updatedFilm.getReleaseDate(),
                updatedFilm.getDuration(),
                updatedFilm.getMpa().getId(),
                updatedFilm.getId());

        insertGenres(updatedFilm);

        if (valid == 0) {
            throw new UserNotFoundException();
        } else {
            return updatedFilm;
        }
    }

    @Override
    public void addLike(int filmId, int userId) { // добавление лайка
        log.info("FilmsDbStorage: добавление лайка фильму с id: {} от пользователя с id: {}", filmId, userId);
        String sqlQuery = "INSERT INTO likes (film_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public void removeLike(int filmId, int userId) {
        log.info("FilmsDbStorage: удаление ллайка фульма с id: {} от пользователя с id: {}", filmId, userId);

        String sqlQuery = "DELETE FROM likes WHERE film_id=? AND user_id=?";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public void removeFilmFromStorage(Film removedFilm) {
        log.info("FilmsDbStorage: удаление фильма: {}", removedFilm);
        String sqlQuery = "DELETE FROM films WHERE id=?";
        jdbcTemplate.update(sqlQuery, removedFilm.getId());
    }

    private final class FilmMapper implements RowMapper<Film> {
        @Override
        public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
            Film film = new Film();

            film.setId(rs.getInt("id"));
            film.setName(rs.getString("name"));
            film.setDescription(rs.getString("description"));
            film.setReleaseDate(rs.getDate("releaseDate").toLocalDate());
            film.setDuration(rs.getInt("duration"));

            Mpa mpa = new Mpa();
            mpa.setId(rs.getInt("mpa"));
            film.setMpa(mpa);

            return film;
        }
    }

    private final class FilmMpaMapper implements RowMapper<Film> {
        @Override
        public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
            Film film = new Film();

            film.setId(rs.getInt("id"));
            film.setName(rs.getString("name"));
            film.setDescription(rs.getString("description"));
            film.setReleaseDate(rs.getDate("releaseDate").toLocalDate());
            film.setDuration(rs.getInt("duration"));

            Mpa mpa = new Mpa();
            mpa.setId(rs.getInt("mpa_id"));
            mpa.setName(rs.getString("mpa_name"));
            film.setMpa(mpa);

            return film;
        }
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
