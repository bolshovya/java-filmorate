package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.RatingsStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class RatingsDbStorage implements RatingsStorage {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RatingsDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Rating findById(int id) {
        String sqlQuery = "SELECT * FROM ratings WHERE id=?";
        return jdbcTemplate.queryForObject(sqlQuery, new RatingMapper());
    }

    @Override
    public List<Rating> getListOfAllRatings() {
        String sqlQuery = "SELECT * FROM ratings";
        return jdbcTemplate.query(sqlQuery, new RatingMapper());
    }



    private final class RatingMapper implements RowMapper<Rating> {

        @Override
        public Rating mapRow(ResultSet rs, int rowNum) throws SQLException {
            Rating rating = new Rating();

            rating.setId(rs.getInt("id"));
            rating.setRating(rs.getString("rating"));

            return rating;
        }
    }
}
