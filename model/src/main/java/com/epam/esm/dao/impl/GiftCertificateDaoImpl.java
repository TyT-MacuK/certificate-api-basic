package com.epam.esm.dao.impl;

import com.epam.esm.config.GiftCertificateMapper;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
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
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final Logger logger = LogManager.getLogger();

    private static final String ID_PARAMETER = "id";
    private static final String NAME_PARAMETER = "name";
    private static final String DESCRIPTION_PARAMETER = "description";
    private static final String PRICE_PARAMETER = "price";
    private static final String DURATION_PARAMETER = "duration";
    private static final String CREATE_DATE_PARAMETER = "create_date";
    private static final String LAST_UPDATE_DATE_PARAMETER = "last_update_date";
    //private static final String CERTIFICATE_ID_PARAMETER = "certificate_id";
    //private static final String TAG_ID_PARAMETER = "tag_id";
    //private static final String TAG_NAME_PARAMETER = "tag_name";
    //private static final String CERTIFICATE_NAME_PARAMETER = "certificate_name";

    private static final String SQL_ADD_GIFT_CERTIFICATE = """
            INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
            VALUES (name = :name, description = :description, price = :price, duration = :duration,
            create_date = :create_date, last_update_date = :last_update_date)
            """;
    private static final String SQL_FIND_BY_ID = """
            SELECT id, name, description, price, duration, create_date, last_update_date FROM gift_certificate
            WHERE id = :id
            """;
    private static final String SQL_FIND_BY_TAG_NAME = """
            SELECT gift_certificate.id, gift_certificate.name, description, price, duration, create_date, last_update_date
            FROM gift_certificate
            JOIN gift_certificate_has_tag ON gift_certificate.id = gift_certificate_has_tag.gift_certificate_id
            JOIN tag ON tag.id = gift_certificate_has_tag.tag_id
            WHERE tag.name = :name
                        """;
    private static final String SQL_FIND_BY_PART_OF_TAG_NAME = """
            SELECT DISTINCT gift_certificate.id, gift_certificate.name, description, price, duration, create_date, last_update_date
            FROM gift_certificate
            JOIN gift_certificate_has_tag ON gift_certificate.id = gift_certificate_has_tag.gift_certificate_id
            JOIN tag ON tag.id = gift_certificate_has_tag.tag_id
            WHERE tag.name LIKE CONCAT ('%', :name, '%')
                        """;
    private static final String SQL_SORT = """
            SELECT gift_certificate.id, gift_certificate.name, description, price, duration, create_date, last_update_date
            FROM gift_certificate
            ORDER BY gift_certificate.name
            """;
    private static final String SQL_UPDATE_GIFT_CERTIFICATE = """
            UPDATE gift_certificate SET name = :name, description = :description, price = :price,
            duration = :duration, last_update_date = :last_update_date
            WHERE id = :id
            """;
    private static final String SQL_DELETE_GIFT_CERTIFICATE = "DELETE FROM gift_certificate WHERE id = :id";


    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final GiftCertificateMapper mapper;

    @Autowired
    public GiftCertificateDaoImpl(DataSource dataSource, GiftCertificateMapper mapper) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.mapper = mapper;
    }

    @Override
    public boolean add(GiftCertificate certificate) {
        logger.log(Level.DEBUG, "method add()");
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(NAME_PARAMETER, certificate.getName())
                .addValue(DESCRIPTION_PARAMETER, certificate.getDescription())
                .addValue(PRICE_PARAMETER, certificate.getPrice())
                .addValue(DURATION_PARAMETER, certificate.getDuration())
                .addValue(CREATE_DATE_PARAMETER, certificate.getCreateDay())
                .addValue(LAST_UPDATE_DATE_PARAMETER, certificate.getLastUpdateDay());
        return jdbcTemplate.update(SQL_ADD_GIFT_CERTIFICATE, parameters) > 0;
    }

    @Override
    public Optional<GiftCertificate> findById(Long id) {
        logger.log(Level.DEBUG, "method findById()");
        SqlParameterSource parameters = new MapSqlParameterSource().addValue(ID_PARAMETER, id);
        return jdbcTemplate.query(SQL_FIND_BY_ID, parameters, mapper).stream().findAny();
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {//TODO
        return Optional.empty();
    }

    @Override
    public Optional<GiftCertificate> findByTagName(String tagName) {
        logger.log(Level.DEBUG, "method findByTagName()");
        SqlParameterSource parameters = new MapSqlParameterSource().addValue(NAME_PARAMETER, tagName);
        return jdbcTemplate.query(SQL_FIND_BY_TAG_NAME, parameters, mapper).stream().findAny();
    }

    @Override
    public List<GiftCertificate> findByPartOfTagName(String partOfTagName) {
        logger.log(Level.DEBUG, "method findByPartOfTagName()");
        SqlParameterSource parameters = new MapSqlParameterSource().addValue(NAME_PARAMETER, partOfTagName);
        return jdbcTemplate.query(SQL_FIND_BY_PART_OF_TAG_NAME, parameters, mapper);
    }

    @Override
    public List<GiftCertificate> sortCertificate(String sortOrder) {
        logger.log(Level.DEBUG, "method findAllCertificate()");
        return jdbcTemplate.query(SQL_SORT.concat(sortOrder), mapper);
    }

    @Override
    public boolean updateName(GiftCertificate certificate) {
        logger.log(Level.DEBUG, "method updateName()");
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(ID_PARAMETER, certificate.getId())
                .addValue(NAME_PARAMETER, certificate.getName())
                .addValue(DESCRIPTION_PARAMETER, certificate.getDescription())
                .addValue(PRICE_PARAMETER, certificate.getPrice())
                .addValue(DURATION_PARAMETER, certificate.getDuration())
                .addValue(LAST_UPDATE_DATE_PARAMETER, certificate.getLastUpdateDay());
        return jdbcTemplate.update(SQL_UPDATE_GIFT_CERTIFICATE, parameters) > 0;
    }

    @Override
    public boolean delete(Long id) {
        logger.log(Level.DEBUG, "method delete()");
        SqlParameterSource parameters = new MapSqlParameterSource(ID_PARAMETER, id);
        return jdbcTemplate.update(SQL_DELETE_GIFT_CERTIFICATE, parameters) > 0;
    }
}
