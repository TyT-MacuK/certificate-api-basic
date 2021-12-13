package com.epam.esm.handler;

import com.epam.esm.exception.*;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String ENTITY_ALREADY_EXISTS_MESSAGE = "entity_already_exists";
    private static final String ENTITY_NOT_FOUND_MESSAGE = "entity_not_found";
    private static final String INVALID_ENTITY_MESSAGE = "invalid_entity";
    private static final String INVALID_NAME_MESSAGE = "invalid_entity.name";
    private static final String INVALID_DESCRIPTION_MESSAGE = "invalid_entity.description";
    private static final String INVALID_PRICE_MESSAGE = "invalid_entity.price";
    private static final String INVALID_DURATION_MESSAGE = "invalid_entity.duration";
    private static final String INVALID_SORT_ORDER_NAME = "invalid_sort_order_name";
    private static final String UNFORESEEN_EXCEPTION = "unforeseen_exception";

    private static final int ENTITY_ALREADY_EXISTS_CODE = 40901;
    private static final int ENTITY_NOT_FOUND_CODE = 40401;
    private static final int SORT_NAME_NOT_FOUND_CODE = 40001;
    private static final int INVALID_ENTITY_CODE = 40002;
    private static final int WRONG_WORK_CODE = 41801;

    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ENTITY_ERRORS = "entityErrors";
    private static final String ERROR_CODE = "errorCode";

    private final ResourceBundleMessageSource messageSource;

    public ApplicationExceptionHandler(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<Object> handlerEntityAlreadyExist() {
        String message = getLocalizedErrorMessage(ENTITY_ALREADY_EXISTS_MESSAGE);
        return buildErrorResponseEntity(message, ENTITY_ALREADY_EXISTS_CODE, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handlerEntityNotFound(EntityNotFoundException e) {
        String message = getLocalizedErrorMessage(ENTITY_NOT_FOUND_MESSAGE);
        return buildErrorResponseEntity(message, ENTITY_NOT_FOUND_CODE, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidEntityDataException.class)
    public ResponseEntity<Object> handlerInvalidEntityData(InvalidEntityDataException e) {
        List<TypeOfValidationError> errorList = e.getValidationErrorList();
        List<String> errorMessageList = new ArrayList<>();
        for (TypeOfValidationError validationError : errorList) {
            errorMessageList.add(
                    switch (validationError) {
                        case INVALID_NAME -> getLocalizedErrorMessage(INVALID_NAME_MESSAGE);
                        case INVALID_DESCRIPTION -> getLocalizedErrorMessage(INVALID_DESCRIPTION_MESSAGE);
                        case INVALID_PRICE -> getLocalizedErrorMessage(INVALID_PRICE_MESSAGE);
                        case INVALID_DURATION -> getLocalizedErrorMessage(INVALID_DURATION_MESSAGE);
                    }
            );
        }
        Class<?> causeEntity = e.getCauseEntity();
        String message = String.format(getLocalizedErrorMessage(INVALID_ENTITY_MESSAGE), causeEntity.getSimpleName());
        Map<String, Object> responseMessage = new HashMap<>();
        responseMessage.put(ERROR_MESSAGE, message);
        responseMessage.put(ENTITY_ERRORS, errorMessageList);
        responseMessage.put(ERROR_CODE, INVALID_ENTITY_CODE);
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidSortOderNameException.class)
    public ResponseEntity<Object> handlerInvalidSortOderName() {
        String message = getLocalizedErrorMessage(INVALID_SORT_ORDER_NAME);
        return buildErrorResponseEntity(message, SORT_NAME_NOT_FOUND_CODE, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handlerUnforeseenException() {
        String message = getLocalizedErrorMessage(UNFORESEEN_EXCEPTION);
        return buildErrorResponseEntity(message, WRONG_WORK_CODE, HttpStatus.I_AM_A_TEAPOT);
    }

    private String getLocalizedErrorMessage(String message) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(message, null, locale);
    }

    private ResponseEntity<Object> buildErrorResponseEntity(String errorMessage, int errorCode, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put(ERROR_MESSAGE, errorMessage);
        body.put(ERROR_CODE, errorCode);
        return new ResponseEntity<>(body, status);
    }
}
