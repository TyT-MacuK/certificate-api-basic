package com.epam.esm.handler;

import com.epam.esm.exception.EntityAlreadyExistsException;
import com.epam.esm.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String ENTITY_ALREADY_EXISTS_MESSAGE = "entity_already_exists";
    private static final String ENTITY_NOT_FOUND_MESSAGE = "entity_not_found";
    private static final String METHOD_NOT_ALLOWED_MESSAGE = "method_not_allowed";
    private static final String BAD_REQUEST = "bad_request";
    private static final String USER_NOT_FOUND_MESSAGE = "user_not_found";

    private static final int BAD_REQUEST_CODE = 40000;
    private static final int INVALID_ENTITY_CODE = 40001;
    private static final int ENTITY_NOT_FOUND_CODE = 40401;
    private static final int METHOD_NOT_ALLOWED_CODE = 40501;
    private static final int ENTITY_ALREADY_EXISTS_CODE = 40901;

    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_CODE = "errorCode";

    private static final String LOCALE_REQUEST_PARAMETER = "loc";

    private final MessageSource messageSource;

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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        List<String> messages = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> messages.add(error.getDefaultMessage()));

        Map<String, Object> responseMessage = new HashMap<>();
        responseMessage.put(ERROR_MESSAGE, messages);
        responseMessage.put(ERROR_CODE, INVALID_ENTITY_CODE);
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        List<String> messages = new ArrayList<>();
        e.getConstraintViolations().forEach(error -> messages.add(error.getMessage()));

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


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleWrongUsernameException() {
        String message = getLocalizedErrorMessage(USER_NOT_FOUND_MESSAGE);
        return buildErrorResponseEntity(message, ENTITY_NOT_FOUND_CODE, NOT_FOUND);
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
