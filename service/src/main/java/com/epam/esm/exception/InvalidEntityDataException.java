package com.epam.esm.exception;

import java.util.List;

public class InvalidEntityDataException extends Exception {
    private final List<TypeOfValidationError> errorList;
    private final Class<?> causeEntity;

    public InvalidEntityDataException(List<TypeOfValidationError> errorList, Class<?> causeEntity) {
        this.errorList = errorList;
        this.causeEntity = causeEntity;
    }

    public List<TypeOfValidationError> getValidationErrorList() {
        return errorList;
    }

    public Class<?> getCauseEntity() {
        return causeEntity;
    }
}
