package com.epam.esm.dao.impl;

import com.epam.esm.config.TagMapper;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {
    private static final Logger logger = LogManager.getLogger();

    private static final String SQL_ADD_TAG = "INSERT INTO tag (name) VALUES (:name)";
    private static final String SQL_FIND_BY_ID = "SELECT id, name FROM tag WHERE id = :id";
    private static final String SQL_FIND_BY_NAME = "SELECT id, name FROM tag WHERE name = :name";
    private static final String SQL_DELETE_TAG = "DELETE FROM tag WHERE id = :id";
    private static final String ID_PARAMETER = "id";
    private static final String NAME_PARAMETER = "name";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final TagMapper mapper;

    @Autowired
    public TagDaoImpl(DataSource dataSource, TagMapper mapper) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.mapper = mapper;
    }

    @Override
    public boolean add(Tag tag) {
        logger.log(Level.DEBUG, "method add()");
        SqlParameterSource parameters = new MapSqlParameterSource().addValue(NAME_PARAMETER, tag.getName());
        return jdbcTemplate.update(SQL_ADD_TAG, parameters) > 0;
    }

    @Override
    public Optional<Tag> findById(Long id) {
        logger.log(Level.DEBUG, "method findById()");
        SqlParameterSource parameters = new MapSqlParameterSource().addValue(ID_PARAMETER, id);
        List<Tag> tagList = jdbcTemplate.query(SQL_FIND_BY_ID, parameters, mapper);
        return tagList.stream().findAny();
    }

    @Override
    public Optional<Tag> findByName(String name) {
        logger.log(Level.DEBUG, "method findByName()");
        SqlParameterSource parameters = new MapSqlParameterSource().addValue(ID_PARAMETER, name);
        List<Tag> tagList = jdbcTemplate.query(SQL_FIND_BY_NAME, parameters, mapper);
        return tagList.stream().findAny();
    }

    @Override
    public boolean delete(Long id) {
        logger.log(Level.DEBUG, "method delete()");
        SqlParameterSource parameters = new MapSqlParameterSource().addValue(ID_PARAMETER, id);
        return jdbcTemplate.update(SQL_DELETE_TAG, parameters) > 0;
    }
}
