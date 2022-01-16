package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateSearchParamsDto;
import com.epam.esm.exception.TypeOfValidationError;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
@Log4j2
public class SearchParamsValidator {
    private static final String NAME_REGEX = "^[^<>]{1,30}$";
    private static final String DESCRIPTION_REGEX = "^[^<>]{1,500}$";
    private static final String ASCENDING = "ASC";
    private static final String DESCENDING = "DESC";

    /**
     * Is gift certificate search params valid.
     *
     * @param searchParams the search params
     * @return the list errors
     */
    public List<TypeOfValidationError> isGiftCertificateSearchParamsDtoValid(GiftCertificateSearchParamsDto searchParams) {
        List<TypeOfValidationError> validationErrors = new ArrayList<>();
        List<String> tagNames = searchParams.getTagNames();
        if (tagNames != null && !tagNames.isEmpty()) {
            for (String name : tagNames) {
                if (name != null && !isNameValid(name)) {
                    log.warn("invalid tag name: " + name);
                    validationErrors.add(TypeOfValidationError.INVALID_TAG_NAME);
                }
            }
        }
        if (searchParams.getCertificateName() != null && !isNameValid(searchParams.getCertificateName())) {
            log.warn("invalid certificate name: " + searchParams.getCertificateName());
            validationErrors.add(TypeOfValidationError.INVALID_CERTIFICATE_NAME);
        }
        if (searchParams.getCertificateDescription() != null && !isDescriptionValid(searchParams.getCertificateDescription())) {
            log.warn("invalid description: " + searchParams.getCertificateDescription());
            validationErrors.add(TypeOfValidationError.INVALID_DESCRIPTION);
        }
        if (searchParams.getOrderByName() != null && !isSortOrderValid(searchParams.getOrderByName())) {
            log.warn("invalid order by name param: " + searchParams.getOrderByName());
            validationErrors.add(TypeOfValidationError.INVALID_ORDER_BY_NAME_PARAM);
        }
        if (searchParams.getOrderByCreateDate() != null && !isSortOrderValid(searchParams.getOrderByCreateDate())) {
            log.warn("invalid order by create date param: " + searchParams.getOrderByCreateDate());
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
     * Is type sort order valid.
     *
     * @param sortOrder the sort order
     * @return the boolean
     */
    public boolean isSortOrderValid(String sortOrder) {
        return ASCENDING.equalsIgnoreCase(sortOrder) || DESCENDING.equalsIgnoreCase(sortOrder);
    }
}
