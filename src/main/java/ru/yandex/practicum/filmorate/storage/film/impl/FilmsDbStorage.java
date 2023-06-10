package ru.yandex.practicum.filmorate.storage.film.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmsStorage;
import ru.yandex.practicum.filmorate.storage.user.impl.UsersDbStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class FilmsDbStorage implements FilmsStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmsDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film addFilm(Film addedFilm) {
        jdbcTemplate.update("INSERT INTO films (name, description, releaseDate, duration) VALUES(?, ?, ?, ?)",
                addedFilm.getName(),
                addedFilm.getDescription(),
                addedFilm.getReleaseDate(),
                addedFilm.getDuration());
        return addedFilm;
    }

    @Override
    public Film updateFilm(Film updatedFilm) {
        jdbcTemplate.update("UPDATE films SET name=?, description=?, releaseDate=?, duration=?, WHERE id=?",
                updatedFilm.getName(),
                updatedFilm.getDescription(),
                updatedFilm.getReleaseDate(),
                updatedFilm.getDuration(),
                updatedFilm.getId());
        return updatedFilm;
    }

    @Override
    public Film removeFilmFromStorage(Film removedFilm) {
        jdbcTemplate.update("DELETE FROM films WHERE id=?", removedFilm.getId());
        return removedFilm;
    }

    @Override
    public Film findById(int id) {
        Optional<Film> optFilm = jdbcTemplate.query("SELECT * FROM films WHERE id=?",
                        new Object[]{id}, new FilmMapper())
                .stream().findAny();
        if (optFilm.isEmpty()) {
            throw new UserNotFoundException();
        }
        return optFilm.get();
    }

    @Override
    public List<Film> getListOfAllFilms() {
        return jdbcTemplate.query("SELECT * FROM films", new FilmMapper());
    }

    public int getSizeStorage() {
        return 0;
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

            return film;
        }
    }



}
