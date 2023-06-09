package ru.yandex.practicum.filmorate.storage.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.*;
import ru.yandex.practicum.filmorate.storage.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Mpa> findById(int id) {
        log.info("MpaDbStorage: получение рейтинга с id: {}", id);
        return jdbcTemplate.query("SELECT * FROM mpa WHERE id=?", new MpaMapper(), id).stream().findAny();
    }

    @Override
    public List<Mpa> getAll() {
        log.info("MpaDbStorage: получение всех рейтингов");
        String sqlQuery = "SELECT * FROM mpa";
        return jdbcTemplate.query(sqlQuery, new MpaMapper());
    }

    @Override
    public String getName(int id) {
        log.info("MpaDbStorage: получение name рейтинга с id: {}", id);
        String sqlQuery = "SELECT name FROM mpa WHERE id=?";
        return jdbcTemplate.queryForObject(sqlQuery, new MpaMapper()).getName();
    }

    private final class MpaMapper implements RowMapper<Mpa> {

        @Override
        public Mpa mapRow(ResultSet rs, int rowNum) throws SQLException {
            Mpa mpa = new Mpa();

            mpa.setId(rs.getInt("id"));
            mpa.setName(rs.getString("name"));

            return mpa;
        }
    }
}
