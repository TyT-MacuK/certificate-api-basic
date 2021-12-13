package com.epam.esm.dao.impl;

import com.epam.esm.config.GiftCertificateMapper;
import com.epam.esm.config.TagMapper;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.GiftCertificate;
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
    private static final String SQL_FIND_BY_PART_OF_TAG_NAME = """
            SELECT id, name FROM tag
            WHERE name LIKE CONCAT ('%', :name, '%')
                        """;
    private static final String SQL_DELETE_TAG = "DELETE FROM tag WHERE id = :id";
    private static final String SQL_FIND_BY_NAME = "SELECT id, name FROM tag WHERE name = :name";
    private static final String SQL_FIND_TAG_CERTIFICATES = """
            SELECT gift_certificate.id, gift_certificate.name, description, price, duration, create_date, last_update_date\s
            FROM gift_certificate
            JOIN gift_certificate_has_tag ON gift_certificate.id = gift_certificate_has_tag.gift_certificate_id
            WHERE tag_id = :tag_id
            """;
    private static final String SQL_DELETE_RELATION_TAG_WITH_GIFT_CERTIFICATES = """
            DELETE FROM gift_certificate_has_tag WHERE tag_id = :tag_id
            """;
    private static final String ID_PARAMETER = "id";
    private static final String NAME_PARAMETER = "name";
    private static final String TAG_ID_PARAMETER = "tag_id";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final TagMapper tagMapper;
    private final GiftCertificateMapper certificateMapper;

    @Autowired
    public TagDaoImpl(NamedParameterJdbcTemplate namedJdbcTemplate, TagMapper tagMapper, GiftCertificateMapper certificateMapper) {
        this.jdbcTemplate = namedJdbcTemplate;
        this.tagMapper = tagMapper;
        this.certificateMapper = certificateMapper;
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
        List<Tag> tagList = jdbcTemplate.query(SQL_FIND_BY_ID, parameters, tagMapper);
        return tagList.stream().findAny();
    }

    @Override
    public List<Tag> findByPartOfName(String partOfName) {
        logger.log(Level.DEBUG, "method findByPartOfName()");
        SqlParameterSource parameters = new MapSqlParameterSource().addValue(NAME_PARAMETER, partOfName);
        return jdbcTemplate.query(SQL_FIND_BY_PART_OF_TAG_NAME, parameters, tagMapper);
    }

    @Override
    public boolean delete(Long id) {
        logger.log(Level.DEBUG, "method delete()");
        SqlParameterSource parameters = new MapSqlParameterSource().addValue(ID_PARAMETER, id);
        return jdbcTemplate.update(SQL_DELETE_TAG, parameters) > 0;
    }

    @Override
    public Optional<Tag> findByName(String name) {
        logger.log(Level.DEBUG, "method findByName()");
        SqlParameterSource parameters = new MapSqlParameterSource().addValue(NAME_PARAMETER, name);
        List<Tag> tagList = jdbcTemplate.query(SQL_FIND_BY_NAME, parameters, tagMapper);
        return tagList.stream().findAny();
    }

    @Override
    public List<GiftCertificate> findTagCertificates(Long id) {
        logger.log(Level.DEBUG, "method findTagCertificates()");
        SqlParameterSource parameters = new MapSqlParameterSource().addValue(TAG_ID_PARAMETER, id);
        return jdbcTemplate.query(SQL_FIND_TAG_CERTIFICATES, parameters, certificateMapper);
    }

    @Override
    public boolean detachAllCertificates(Long tagId) {
        logger.log(Level.DEBUG, "method detachAllCertificates()");
        SqlParameterSource parameters = new MapSqlParameterSource(TAG_ID_PARAMETER, tagId);
        return jdbcTemplate.update(SQL_DELETE_RELATION_TAG_WITH_GIFT_CERTIFICATES, parameters) > 0;
    }
}
