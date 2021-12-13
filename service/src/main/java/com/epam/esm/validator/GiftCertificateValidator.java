package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.TypeOfValidationError;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * The Gift certificate validator.
 */
@Component
public class GiftCertificateValidator {
    private static final Logger logger = LogManager.getLogger();
    private static final String NAME_REGEX = "^[^<>]{1,30}$";
    private static final String DESCRIPTION_REGEX = "^[^<>]{1,500}$";
    private static final int PRICE_MIN_VALUE = 0;
    private static final int DURATION_MIN_VALUE = 0;
    private static final String ASCENDING = "ASC";
    private static final String DESCENDING = "DESC";

    /**
     * Is gift certificate valid.
     *
     * @param giftCertificateDto the gift certificate dto
     * @return the list
     */
    public List<TypeOfValidationError> isGiftCertificateValid(GiftCertificateDto giftCertificateDto) {
        logger.log(Level.DEBUG, "method isGiftCertificateValid()");
        List<TypeOfValidationError> validationErrors = new ArrayList<>();
        if (!isNameValid(giftCertificateDto.getName())) {
            validationErrors.add(TypeOfValidationError.INVALID_NAME);
        }
        if (!isDescriptionValid(giftCertificateDto.getDescription())) {
            validationErrors.add(TypeOfValidationError.INVALID_DESCRIPTION);
        }
        if (!isPriceValid(giftCertificateDto.getPrice())) {
            validationErrors.add(TypeOfValidationError.INVALID_PRICE);
        }
        if (!isDurationValid(giftCertificateDto.getDuration())) {
            validationErrors.add(TypeOfValidationError.INVALID_DURATION);
        }
        return validationErrors;
    }

    /**
     * Is type sort order valid.
     *
     * @param sortOrder the sort order
     * @return the boolean
     */
    public boolean isSortOrderValid(String sortOrder) {
        return ASCENDING.equalsIgnoreCase(sortOrder) || DESCENDING.equalsIgnoreCase(sortOrder);
    }

    private boolean isNameValid(String name) {
        return name != null && Pattern.matches(NAME_REGEX, name);
    }

    private boolean isDescriptionValid(String description) {
        return description != null && Pattern.matches(DESCRIPTION_REGEX, description);
    }

    private boolean isPriceValid(BigDecimal price) {
        return price != null && price.compareTo(BigDecimal.ZERO) > PRICE_MIN_VALUE;
    }

    private boolean isDurationValid(int duration) {
        return duration > DURATION_MIN_VALUE;
    }
}
