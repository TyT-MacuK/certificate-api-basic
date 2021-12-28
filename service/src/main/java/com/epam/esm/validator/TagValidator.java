package com.epam.esm.validator;

import com.epam.esm.exception.TypeOfValidationError;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * The Tag validator.
 */
@Component
@Log4j2
public class TagValidator {
    private static final String NAME_REGEX = "[a-zA-Z]{1,30}";

    /**
     * Validate tag name.
     *
     * @param tagName the tag name
     * @return the list
     */
    public List<TypeOfValidationError> validateName(String tagName) {
        List<TypeOfValidationError> validationErrors = new ArrayList<>();

        boolean nameIsValid = tagName != null && !tagName.isEmpty() && Pattern.matches(NAME_REGEX, tagName);
        if (!nameIsValid) {
            log.log(Level.WARN, "tag name {} is invalid", tagName);
            validationErrors.add(TypeOfValidationError.INVALID_NAME);
        }
        return validationErrors;
    }
}
