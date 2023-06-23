package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationFilmsException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.FilmsStorage;
import ru.yandex.practicum.filmorate.storage.GenresStorage;
import ru.yandex.practicum.filmorate.storage.RatingsStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
public class FilmsDbStorage implements FilmsStorage {

    private final JdbcTemplate jdbcTemplate;
    private final RatingsStorage ratingsStorage;
    private final GenresStorage genresStorage;
    private final static String SQL_QUERY_FILM_BY_ID = "SELECT * FROM films WHERE id=?";

    @Autowired
    public FilmsDbStorage(JdbcTemplate jdbcTemplate, RatingsStorage ratingsStorage, GenresStorage genresStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.ratingsStorage = ratingsStorage;
        this.genresStorage = genresStorage;
    }


    // create
    @Override
    public Film addFilm(Film addedFilm) {
        Film film = validation(addedFilm);
        String sqlQueryInsertFilm = "INSERT INTO films (name, description, releaseDate, duration, rating) VALUES (?, ?, ?, ?, ?)";
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

        // return jdbcTemplate.queryForObject(SQL_QUERY_FILM_BY_ID, new FilmMapper(), new Object[]{filmKey});
        addedFilm.setId(filmKey);

        String sqlQueryInsertGenre = "INSERT INTO filmGenre (film_id, genre_id) VALUES (?, ?)";
        Set<Genre> filmGenres = addedFilm.getGenres();
        for (Genre genre : filmGenres) {
            jdbcTemplate.update(sqlQueryInsertGenre, addedFilm.getId(), genre.getId());
        }
        return addedFilm;
    }

    public Film validation(Film addedFilm) {
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

    //read
    /*
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

     */

    @Override
    public Film findById(int id) throws FilmNotFoundException {
        String sqlQuery = "SELECT * FROM films WHERE id=?";
        if (jdbcTemplate.queryForObject(
                "select count(*) from films where id = ?", Integer.class, id) == 0) {
            throw new FilmNotFoundException();
        }
        return jdbcTemplate.queryForObject(sqlQuery, new FilmMapper(), new Object[] {id});
    }




    @Override
    public List<Film> getListOfAllFilms() {
        String sqlQuery = "SELECT * FROM films";
        return jdbcTemplate.query(sqlQuery, new FilmMapper());
    }

    // update
    @Override
    public Film updateFilm(Film updatedFilm) {
        String sqlQuery = "UPDATE films SET name=?, description=?, releaseDate=?, duration=?, rating=? WHERE id=?";
        int valid = jdbcTemplate.update(sqlQuery,
                updatedFilm.getName(),
                updatedFilm.getDescription(),
                updatedFilm.getReleaseDate(),
                updatedFilm.getDuration(),
                updatedFilm.getMpa().getId(),
                updatedFilm.getId());
        if (valid == 0) {
            throw new UserNotFoundException();
        } else {
            return jdbcTemplate.queryForObject(SQL_QUERY_FILM_BY_ID, new FilmMapper(), new Object[]{updatedFilm.getId()});
        }
    }

    @Override
    public Film removeFilmFromStorage(Film removedFilm) {
        jdbcTemplate.update("DELETE FROM films WHERE id=?", removedFilm.getId());

        return jdbcTemplate.queryForObject(SQL_QUERY_FILM_BY_ID, new FilmMapper(), new Object[]{removedFilm.getId()});
    }

    public int getSizeStorage() {
        String sqlQuery = "SELECT COUNT(id) FROM films";

        return jdbcTemplate.queryForObject(sqlQuery, Integer.class);
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

            Rating mpa = new Rating();
            mpa.setId(rs.getInt("rating"));
            film.setMpa(mpa);

            return film;
        }
    }



}
