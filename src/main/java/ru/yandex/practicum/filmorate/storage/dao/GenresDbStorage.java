package ru.yandex.practicum.filmorate.storage.dao;

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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GenresDbStorage implements GenresStorage {

    private final JdbcTemplate jdbcTemplate;

    private final String SQL_QUERY_GENRE_BY_ID = "SELECT * FROM genres WHERE id=?";

    @Autowired
    public GenresDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Genre addGenre(Genre genre) {
        String sqlQuery = "INSERT INTO genres (genre) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement stmt = con.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, genre.getGenre());
            return stmt;
        }, keyHolder);

        int genreKey = keyHolder.getKey().intValue();

        return jdbcTemplate.queryForObject(sqlQuery, new GenreMapper(), new Object[]{genreKey});
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

    @Override
    public Set<Genre> findGenresByFilmID(int filmId) {
        String sqlQuery = "SELECT * g.id, g.genre FROM genres g JOIN filmGenres fg ON g.id = fg.genre_id WHERE fg.film_id =?";
        return jdbcTemplate.query(sqlQuery, new GenreMapper()).stream().collect(Collectors.toSet());
    }

    private final class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            Genre genre = new Genre();

            genre.setId(rs.getInt("id"));
            genre.setGenre(rs.getString("genre"));

            return genre;
        }
    }
}
