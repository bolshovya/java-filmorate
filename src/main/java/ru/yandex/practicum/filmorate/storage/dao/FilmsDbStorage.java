package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.FilmsStorage;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class FilmsDbStorage implements FilmsStorage {

    private final JdbcTemplate jdbcTemplate;
    private final static String SQL_QUERY_FILM_BY_ID = "SELECT * FROM films WHERE id=?";

    @Autowired
    public FilmsDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // create
    @Override
    public Film addFilm(Film film) {
        String sqlQuery = "INSERT INTO films (name, description, releaseDate, duration, rating) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement stmt = con.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);

        int filmKey = keyHolder.getKey().intValue();

        return jdbcTemplate.queryForObject(SQL_QUERY_FILM_BY_ID, new FilmMapper(), new Object[]{filmKey});
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
        String sqlQuery = "UPDATE films SET name=?, description=?, releaseDate=?, duration=?, WHERE id=?";
        jdbcTemplate.update(sqlQuery,
                updatedFilm.getName(),
                updatedFilm.getDescription(),
                updatedFilm.getReleaseDate(),
                updatedFilm.getDuration(),
                updatedFilm.getId());

        return jdbcTemplate.queryForObject(SQL_QUERY_FILM_BY_ID, new FilmMapper(), new Object[]{updatedFilm.getId()});
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
