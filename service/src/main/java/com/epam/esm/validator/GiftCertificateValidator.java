package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificateSearchParamsDto;
import com.epam.esm.exception.TypeOfValidationError;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * The Gift certificate validator.
 */
@Component
@Log4j2
public class GiftCertificateValidator {
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
     * @return the list errors
     */
    public List<TypeOfValidationError> isGiftCertificateValid(GiftCertificateDto giftCertificateDto) {
        List<TypeOfValidationError> validationErrors = new ArrayList<>();
        if (giftCertificateDto.getName() == null || !isNameValid(giftCertificateDto.getName())) {
            log.log(Level.WARN, "invalid name " + giftCertificateDto.getName());
            validationErrors.add(TypeOfValidationError.INVALID_NAME);
        }
        if (giftCertificateDto.getDescription() == null || !isDescriptionValid(giftCertificateDto.getDescription())) {
            log.log(Level.WARN, "invalid description " + giftCertificateDto.getDescription());
            validationErrors.add(TypeOfValidationError.INVALID_DESCRIPTION);
        }
        if (giftCertificateDto.getPrice() == null || !isPriceValid(giftCertificateDto.getPrice())) {
            log.log(Level.WARN, "invalid price " + giftCertificateDto.getPrice());
            validationErrors.add(TypeOfValidationError.INVALID_PRICE);
        }
        if (!isDurationValid(giftCertificateDto.getDuration())) {
            log.log(Level.WARN, "invalid duration " + giftCertificateDto.getDuration());
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
        log.log(Level.DEBUG, "method isSortOrderValid()");
        return ASCENDING.equalsIgnoreCase(sortOrder) || DESCENDING.equalsIgnoreCase(sortOrder);
    }

    /**
     * Is gift certificate search params valid.
     *
     * @param searchParams the search params
     * @return the list errors
     */
    public List<TypeOfValidationError> isGiftCertificateSearchParamsDtoValid(GiftCertificateSearchParamsDto searchParams) {
        List<TypeOfValidationError> validationErrors = new ArrayList<>();
        if (searchParams.getTagName() != null && !isNameValid(searchParams.getTagName())) {
            log.log(Level.WARN, "invalid tag name " + searchParams.getTagName());
            validationErrors.add(TypeOfValidationError.INVALID_TAG_NAME);
        }
        if (searchParams.getCertificateName() != null && !isNameValid(searchParams.getCertificateName())) {
            log.log(Level.WARN, "invalid certificate name " + searchParams.getCertificateName());
            validationErrors.add(TypeOfValidationError.INVALID_CERTIFICATE_NAME);
        }
        if (searchParams.getCertificateDescription() != null && !isDescriptionValid(searchParams.getCertificateDescription())) {
            log.log(Level.WARN, "invalid description " + searchParams.getCertificateDescription());
            validationErrors.add(TypeOfValidationError.INVALID_DESCRIPTION);
        }
        if (searchParams.getOrderByName() != null && !isSortOrderValid(searchParams.getOrderByName())) {
            log.log(Level.WARN, "invalid order by name param " + searchParams.getOrderByName());
            validationErrors.add(TypeOfValidationError.INVALID_ORDER_BY_NAME_PARAM);
        }
        if (searchParams.getOrderByCreateDate() != null && !isSortOrderValid(searchParams.getOrderByCreateDate())) {
            log.log(Level.WARN, "invalid order by create date param " + searchParams.getOrderByCreateDate());
            validationErrors.add(TypeOfValidationError.INVALID_ORDER_BY_NAME_PARAM);
        }
        return validationErrors;
    }

    /**
     * Is name valid.
     *
     * @param name the name
     * @return the boolean
     */
    public boolean isNameValid(String name) {
        return Pattern.matches(NAME_REGEX, name);
    }

    /**
     * Is description valid.
     *
     * @param description the description
     * @return the boolean
     */
    public boolean isDescriptionValid(String description) {
        return Pattern.matches(DESCRIPTION_REGEX, description);
    }

    /**
     * Is price valid.
     *
     * @param price the price
     * @return the boolean
     */
    public boolean isPriceValid(BigDecimal price) {
        return price.compareTo(BigDecimal.ZERO) > PRICE_MIN_VALUE;
    }

    /**
     * Is duration valid.
     *
     * @param duration the duration
     * @return the boolean
     */
    public boolean isDurationValid(int duration) {
        return duration > DURATION_MIN_VALUE;
    }
}
