package com.epam.esm.dao.impl;

import com.epam.esm.config.GiftCertificateMapper;
import com.epam.esm.config.TagMapper;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String ID_PARAMETER = "id";
    private static final String NAME_PARAMETER = "name";
    private static final String DESCRIPTION_PARAMETER = "description";
    private static final String PRICE_PARAMETER = "price";
    private static final String DURATION_PARAMETER = "duration";
    private static final String CREATE_DATE_PARAMETER = "create_date";
    private static final String LAST_UPDATE_DATE_PARAMETER = "last_update_date";
    private static final String GIFT_CERTIFICATE_ID_PARAMETER = "gift_certificate_id";
    private static final String GIFT_CERTIFICATE_NAME_PARAMETER = "gift_certificate.name";
    private static final String TAG_ID_PARAMETER = "tag_id";
    private static final String TAG_NAME_PARAMETER = "tag.name";

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
    private static final String SQL_FIND_BY_PARAMS_PART = """
            SELECT gift_certificate.id, gift_certificate.name, description, price, duration, create_date, last_update_date FROM gift_certificate
            JOIN gift_certificate_has_tag ON gift_certificate.id = gift_certificate_has_tag.tag_id
            JOIN tag ON tag.id =  gift_certificate_has_tag.tag_id
            """;
    private static final String PART_OF_REQUEST_WHERE = " WHERE ";
    private static final String PART_OF_REQUEST_AND = " AND ";
    private static final String PART_OF_REQUEST_LIKE_CONCAT = " LIKE CONCAT ";
    private static final String PART_OF_REQUEST_ORDER_BY = " ORDER BY ";
    private static final String PART_OF_REQUEST_SPASE = " ";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final GiftCertificateMapper certificateMapper;
    private final TagMapper tagMapper;

    @Override
    public boolean add(GiftCertificate certificate) {
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
        SqlParameterSource parameters = new MapSqlParameterSource().addValue(ID_PARAMETER, id);
        return jdbcTemplate.query(SQL_FIND_BY_ID, parameters, certificateMapper).stream().findAny();
    }

    @Override
    public List<GiftCertificate> findByParams(String tagName, String certificateName, String certificateDescription,
                                              String orderByName, String orderByCreateDate) {
        String partWithWhere = createRequestPartWithWhere(tagName, certificateName, certificateDescription);
        String partWithOrder = createRequestPartWithOrder(orderByName, orderByCreateDate);
        String request = SQL_FIND_BY_PARAMS_PART + partWithWhere + partWithOrder;
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(TAG_NAME_PARAMETER, tagName)
                .addValue(GIFT_CERTIFICATE_NAME_PARAMETER, certificateName)
                .addValue(DESCRIPTION_PARAMETER, certificateDescription);
        return jdbcTemplate.query(request, parameters, certificateMapper);
    }

    @Override
    public List<Tag> findCertificateTags(Long giftCertificateId) {
        SqlParameterSource parameters = new MapSqlParameterSource(GIFT_CERTIFICATE_ID_PARAMETER, giftCertificateId);
        return jdbcTemplate.query(SQL_FIND_CERTIFICATE_TAGS, parameters, tagMapper);
    }

    @Override
    public boolean update(GiftCertificate certificate) {
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
        SqlParameterSource parameters = new MapSqlParameterSource(ID_PARAMETER, id);
        return jdbcTemplate.update(SQL_DELETE_GIFT_CERTIFICATE, parameters) > 0;
    }

    @Override
    public boolean detachAllTags(Long giftCertificateId) {
        SqlParameterSource parameters = new MapSqlParameterSource(GIFT_CERTIFICATE_ID_PARAMETER, giftCertificateId);
        return jdbcTemplate.update(SQL_DELETE_RELATION_GIFT_CERTIFICATE_WITH_TAGS, parameters) > 0;
    }

    @Override
    public boolean attach(Long giftCertificateId, Long tagId) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue(GIFT_CERTIFICATE_ID_PARAMETER, giftCertificateId)
                .addValue(TAG_ID_PARAMETER, tagId);
        return jdbcTemplate.update(SQL_MAKE_RELATION_BETWEEN_GIFT_CERTIFICATE_AND_TAG, parameters) > 0;
    }

    private String createRequestPartWithWhere(String tagName, String certificateName, String certificateDescription) {
        StringBuilder builder = new StringBuilder();
        if (tagName != null && !tagName.isBlank()) {
            builder.append(PART_OF_REQUEST_WHERE).append(TAG_NAME_PARAMETER);
            builder.append(PART_OF_REQUEST_SPASE).append(PART_OF_REQUEST_LIKE_CONCAT).append(" ('%', :tag.name, '%') ");
        }
        if (certificateName != null && !certificateName.isBlank()) {
            if (builder.isEmpty()) {
                builder.append(PART_OF_REQUEST_WHERE);
            } else {
                builder.append(PART_OF_REQUEST_AND);
            }
            builder.append(GIFT_CERTIFICATE_NAME_PARAMETER).append(PART_OF_REQUEST_LIKE_CONCAT)
                    .append("('%', :gift_certificate.name, '%') ");
        }
        if (certificateDescription != null && !certificateDescription.isBlank()) {
            if (builder.isEmpty()) {
                builder.append(PART_OF_REQUEST_WHERE);
            } else {
                builder.append(PART_OF_REQUEST_AND);
            }
            builder.append(DESCRIPTION_PARAMETER).append(PART_OF_REQUEST_LIKE_CONCAT)
                    .append("('%', :description, '%') ");
        }
        return builder.toString();
    }

    private String createRequestPartWithOrder(String orderByName, String orderByCreateDate) {
        StringBuilder builder = new StringBuilder();
        if (orderByName != null && !orderByName.isBlank()) {
            builder.append(PART_OF_REQUEST_ORDER_BY).append(GIFT_CERTIFICATE_NAME_PARAMETER)
                    .append(PART_OF_REQUEST_SPASE).append(orderByName);
        }
        if (orderByCreateDate != null && !orderByCreateDate.isBlank()) {
            if (builder.isEmpty()) {
                builder.append(PART_OF_REQUEST_ORDER_BY);
            } else {
                builder.append(", ");
            }
            builder.append(CREATE_DATE_PARAMETER).append(PART_OF_REQUEST_SPASE).append(orderByCreateDate);
        }
        return builder.toString();
    }
}
