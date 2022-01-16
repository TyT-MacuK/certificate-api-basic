package com.epam.esm.validator;

import com.epam.esm.exception.TypeOfValidationError;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Log4j2
@Component
public class UserValidator {
    private static final String NAME_REGEX = "[a-zA-Z]{1,30}";

    /**
     * Validate tag name.
     *
     * @param userName the user name
     * @return the list
     */
    public List<TypeOfValidationError> validateName(String userName) {
        List<TypeOfValidationError> validationErrors = new ArrayList<>();

        boolean nameIsValid = userName != null && !userName.isEmpty() && Pattern.matches(NAME_REGEX, userName);
        if (!nameIsValid) {
            log.warn("user name {} is invalid", userName);
            validationErrors.add(TypeOfValidationError.INVALID_NAME);
        }
        return validationErrors;
    }
}
