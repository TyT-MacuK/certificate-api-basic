package com.epam.esm.handler;


import com.epam.esm.exception.AttachException;
import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.exception.InvalidEntityDataException;
import com.epam.esm.exception.TypeOfValidationError;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.I_AM_A_TEAPOT;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String ENTITY_ALREADY_EXISTS_MESSAGE = "entity_already_exists";
    private static final String ENTITY_NOT_FOUND_MESSAGE = "entity_not_found";
    private static final String INVALID_ENTITY_MESSAGE = "invalid_entity";
    private static final String INVALID_NAME_MESSAGE = "invalid_entity.name";
    private static final String INVALID_DESCRIPTION_MESSAGE = "invalid_entity.description";
    private static final String INVALID_PRICE_MESSAGE = "invalid_entity.price";
    private static final String INVALID_DURATION_MESSAGE = "invalid_entity.duration";
    private static final String INVALID_TAG_NAME_MESSAGE = "invalid_entity.tag_name";
    private static final String INVALID_CERTIFICATE_NAME_MESSAGE = "invalid_entity.certificate_name";
    private static final String INVALID_ORDER_BY_NAME_PARAM_MESSAGE = "invalid_entity.order_by_name_param";
    private static final String INVALID_ORDER_BY_CREATE_DAY_PARAM_MESSAGE = "invalid_entity.order_by_create_date_param";
    private static final String ATTACH_EXCEPTION = "attach_exception";
    private static final String METHOD_NOT_ALLOWED_MESSAGE = "method_not_allowed";
    private static final String BAD_REQUEST = "bad_request";
    private static final String UNFORESEEN_EXCEPTION = "unforeseen_exception";

    private static final int BAD_REQUEST_CODE = 40000;
    private static final int INVALID_ENTITY_CODE = 40001;
    private static final int ENTITY_NOT_FOUND_CODE = 40401;
    private static final int METHOD_NOT_ALLOWED_CODE = 40501;
    private static final int ENTITY_ALREADY_EXISTS_CODE = 40901;
    private static final int WRONG_WORK_CODE = 41801;

    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ENTITY_ERRORS = "entityErrors";
    private static final String ERROR_CODE = "errorCode";

    private static final String LOCALE_REQUEST_PARAMETER = "loc";

    private final ResourceBundleMessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String locale = request.getParameter(LOCALE_REQUEST_PARAMETER);
        if (locale != null) {
            LocaleContextHolder.setLocale(new Locale(locale));
        }
        String message = getLocalizedErrorMessage(BAD_REQUEST);
        return buildErrorResponseEntity(message, BAD_REQUEST_CODE, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String locale = request.getParameter(LOCALE_REQUEST_PARAMETER);
        if (locale != null) {
            LocaleContextHolder.setLocale(new Locale(locale));
        }
        String message = getLocalizedErrorMessage(METHOD_NOT_ALLOWED_MESSAGE);
        return buildErrorResponseEntity(message, METHOD_NOT_ALLOWED_CODE, METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        List<String> messages = new ArrayList<>();
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            messages.add(getLocalizedErrorMessage(constraintViolation.getMessage()));
        }
        Map<String, Object> responseMessage = new HashMap<>();
        responseMessage.put(ERROR_MESSAGE, messages);
        responseMessage.put(ERROR_CODE, INVALID_ENTITY_CODE);
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<Object> handlerEntityAlreadyExist() {
        String message = getLocalizedErrorMessage(ENTITY_ALREADY_EXISTS_MESSAGE);
        return buildErrorResponseEntity(message, ENTITY_ALREADY_EXISTS_CODE, CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handlerEntityNotFound() {
        String message = getLocalizedErrorMessage(ENTITY_NOT_FOUND_MESSAGE);
        return buildErrorResponseEntity(message, ENTITY_NOT_FOUND_CODE, NOT_FOUND);
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
                        case INVALID_TAG_NAME -> getLocalizedErrorMessage(INVALID_TAG_NAME_MESSAGE);
                        case INVALID_CERTIFICATE_NAME -> getLocalizedErrorMessage(INVALID_CERTIFICATE_NAME_MESSAGE);
                        case INVALID_ORDER_BY_NAME_PARAM -> getLocalizedErrorMessage(INVALID_ORDER_BY_NAME_PARAM_MESSAGE);
                        case INVALID_ORDER_BY_CREATE_DAY_PARAM -> getLocalizedErrorMessage(INVALID_ORDER_BY_CREATE_DAY_PARAM_MESSAGE);
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

    @ExceptionHandler(AttachException.class)
    public ResponseEntity<Object> handlerAttachException(AttachException e) {
        String message = getLocalizedErrorMessage(ATTACH_EXCEPTION);
        for (Class<?> entity : e.getNotFoundEntityList()) {
            message = message.concat(entity.getSimpleName()).concat(" ");
        }
        return buildErrorResponseEntity(message, ENTITY_NOT_FOUND_CODE, NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handlerUnforeseenException(Exception e) {
        String message = getLocalizedErrorMessage(UNFORESEEN_EXCEPTION);
        return buildErrorResponseEntity(message + " " + e.getMessage(), WRONG_WORK_CODE, I_AM_A_TEAPOT);
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
