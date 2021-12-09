package com.epam.esm.config;

import com.epam.esm.entity.GiftCertificate;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    private static final Logger logger = LogManager.getLogger();
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String PRICE = "price";
    private static final String DURATION = "duration";
    private static final String CREATE_DATE = "create_date";
    private static final String LAST_UPDATE_DATE = "last_update_date";

    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        logger.log(Level.DEBUG, "method mapRow()");
        return new GiftCertificate.Builder()
                .setId(rs.getLong(ID))
                .setName(rs.getString(NAME))
                .setDescription(rs.getString(DESCRIPTION))
                .setPrice(rs.getBigDecimal(PRICE))
                .setDuration(rs.getInt(DURATION))
                .setCreateDate(rs.getTimestamp(CREATE_DATE).toLocalDateTime())
                .setLastUpdateDate(rs.getTimestamp(LAST_UPDATE_DATE).toLocalDateTime())
                .build();
    }
}
