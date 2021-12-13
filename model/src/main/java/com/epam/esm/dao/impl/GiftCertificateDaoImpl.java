package com.epam.esm.dao.impl;

import com.epam.esm.config.GiftCertificateMapper;
import com.epam.esm.config.TagMapper;
import com.epam.esm.dao.GiftCertificateDao;
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

import java.util.List;
import java.util.Optional;

import static org.springframework.core.env.AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME;

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
    private static final String GIFT_CERTIFICATE_ID_PARAMETER = "gift_certificate_id";
    private static final String TAG_ID_PARAMETER = "tag_id";

    private static final String SQL_ADD_GIFT_CERTIFICATE = """
            INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
            VALUES (:name, :description, :price, :duration, :create_date, :last_update_date)
            """;
    private static final String SQL_FIND_BY_ID = """
            SELECT id, name, description, price, duration, create_date, last_update_date FROM gift_certificate
            WHERE id = :id
            """;
    private static final String SQL_FIND_CERTIFICATE_TAGS = """
            SELECT id, name FROM tag
            JOIN gift_certificate_has_tag ON tag.id = gift_certificate_has_tag.tag_id
            WHERE gift_certificate_id = :gift_certificate_id
            """;
    private static final String SQL_FIND_BY_PART_OF_NAME = """
            SELECT id, name, description, price, duration, create_date, last_update_date
            FROM gift_certificate
            WHERE name LIKE CONCAT ('%', :name, '%')
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
    private static final String SQL_DELETE_RELATION_GIFT_CERTIFICATE_WITH_TAGS = """
            DELETE FROM gift_certificate_has_tag WHERE gift_certificate_id = :gift_certificate_id
            """;
    private static final String SQL_MAKE_RELATION_BETWEEN_GIFT_CERTIFICATE_AND_TAG = """
            INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id)
            VALUES (:gift_certificate_id, :tag_id)
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final GiftCertificateMapper certificateMapper;
    private final TagMapper tagMapper;

    @Autowired
    public GiftCertificateDaoImpl(NamedParameterJdbcTemplate namedJdbcTemplate, GiftCertificateMapper certificateMapper,
                                  TagMapper tagMapper) {
        System.setProperty(ACTIVE_PROFILES_PROPERTY_NAME, "development");
        this.jdbcTemplate = namedJdbcTemplate;
        this.certificateMapper = certificateMapper;
        this.tagMapper = tagMapper;
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
        return jdbcTemplate.query(SQL_FIND_BY_ID, parameters, certificateMapper).stream().findAny();
    }

    @Override
    public List<Tag> findCertificateTags(Long giftCertificateId) {
        logger.log(Level.DEBUG, "method findCertificateTags()");
        SqlParameterSource parameters = new MapSqlParameterSource(GIFT_CERTIFICATE_ID_PARAMETER, giftCertificateId);
        return jdbcTemplate.query(SQL_FIND_CERTIFICATE_TAGS, parameters, tagMapper);
    }

    @Override
    public List<GiftCertificate> findByPartOfName(String partOfName) {
        logger.log(Level.DEBUG, "method findByPartOfTagName()");
        SqlParameterSource parameters = new MapSqlParameterSource().addValue(NAME_PARAMETER, partOfName);
        return jdbcTemplate.query(SQL_FIND_BY_PART_OF_NAME, parameters, certificateMapper);
    }

    @Override
    public List<GiftCertificate> sortCertificate(String sortOrder) {
        logger.log(Level.DEBUG, "method findAllCertificate()");
        return jdbcTemplate.query(SQL_SORT.concat(sortOrder), certificateMapper);
    }

    @Override
    public boolean update(GiftCertificate certificate) {
        logger.log(Level.DEBUG, "method update()");
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

    @Override
    public boolean detachAllTags(Long giftCertificateId) {
        logger.log(Level.DEBUG, "method detachAllTags()");
        SqlParameterSource parameters = new MapSqlParameterSource(GIFT_CERTIFICATE_ID_PARAMETER, giftCertificateId);
        return jdbcTemplate.update(SQL_DELETE_RELATION_GIFT_CERTIFICATE_WITH_TAGS, parameters) > 0;
    }

    @Override
    public boolean attach(Long giftCertificateId, Long tagId) {
        logger.log(Level.DEBUG, "method attachTag()");
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(GIFT_CERTIFICATE_ID_PARAMETER, giftCertificateId)
                .addValue(TAG_ID_PARAMETER, tagId);
        return jdbcTemplate.update(SQL_MAKE_RELATION_BETWEEN_GIFT_CERTIFICATE_AND_TAG, parameters) > 0;
    }
}
