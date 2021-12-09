package com.epam.esm.validator;

import com.epam.esm.exception.TypeOfValidationError;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class TagValidator {
    private static final Logger logger = LogManager.getLogger();
    private static final String NAME_REGEX = "[a-zA-Z]{1,30}";

    public List<TypeOfValidationError> validateName(String tagName) {
        logger.log(Level.DEBUG, "method validateName()");
        List<TypeOfValidationError> validationErrors = new ArrayList<>();

        boolean nameIsValid = !tagName.isEmpty() && Pattern.matches(NAME_REGEX, tagName);
        if (!nameIsValid) {
            validationErrors.add(TypeOfValidationError.INVALID_NAME);
        }
        return validationErrors;
    }
}
