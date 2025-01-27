package com.vedruna.watchlist.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.data.mapping.PropertyReferenceException;

import com.vedruna.watchlist.exception.FilmNotFoundException;
import com.vedruna.watchlist.exception.UserNotFoundException;

import jakarta.persistence.RollbackException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class HandlerExceptionController extends ResponseEntityExceptionHandler{

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        return createExceptionResponse(ex, null, HttpStatus.BAD_REQUEST, request, "Invalid path", null);
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnexpectedTypeException.class)
    protected ResponseEntity<Object> handleUnexpectedType(UnexpectedTypeException ex, WebRequest request) {
        return createExceptionResponse(ex, null, HttpStatus.BAD_REQUEST, request, "Not expected type", null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        return createExceptionResponse(ex, null, HttpStatus.BAD_REQUEST, request, "Argument invalid", null);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex, WebRequest request) {
        Map<String, Object> causes = new HashMap<>();
        causes.put("msg", "There isn't an user with this id");
        return createExceptionResponse(ex, null, HttpStatus.NOT_FOUND, request, ex.getMessage(), causes);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FilmNotFoundException.class)
    protected ResponseEntity<Object> handleFilmNotFound(FilmNotFoundException ex, WebRequest request) {
        Map<String, Object> causes = new HashMap<>();
        causes.put("msg", "There isn't a film with this id");
        return createExceptionResponse(ex, null, HttpStatus.NOT_FOUND, request, ex.getMessage(), causes);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    protected ResponseEntity<Object> handleEmptyResultDataAccess(EmptyResultDataAccessException ex, WebRequest request) {
        Map<String, Object> causes = new HashMap<>();
        causes.put("msg", "There isn't an entity with this id");
        return createExceptionResponse(ex, null, HttpStatus.NOT_FOUND, request, "Entity not found", causes);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        return createExceptionResponse(ex, null, HttpStatus.CONFLICT, request, "Data integrity violation", null);
    }

    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    // @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
            return createExceptionResponse(ex, null, HttpStatus.BAD_REQUEST, request, "Failed to read request", null);
        }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        Map<String, Object> causes = new HashMap<>();

        ex.getConstraintViolations().forEach(error -> {
            causes.put(error.getPropertyPath().toString(), error.getMessage());
        });

        return createExceptionResponse(ex, null, HttpStatus.BAD_REQUEST, request, "Body not valid", causes);
    }

    @Override
    //@ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
            Map<String, Object> causes = new HashMap<>();

            ex.getBindingResult().getFieldErrors().forEach(error -> {
                causes.put(error.getField(),
                            error.getDefaultMessage());
            });

            return createExceptionResponse(ex, headers, status, request, "Body not valid", causes);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    protected ResponseEntity<Object> handleValidation(ValidationException ex, WebRequest request) {
        return createExceptionResponse(ex, null, HttpStatus.BAD_REQUEST, request, "Body not valid", null);
    }
    
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RollbackException.class)
    protected ResponseEntity<Object> handleRollBack(RollbackException ex, WebRequest request) {
        return createExceptionResponse(ex, null, HttpStatus.INTERNAL_SERVER_ERROR, request, "Transaction failed", null);
    }

    @Override
    //@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
        HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
            Map<String, Object> causes = new HashMap<>();
            causes.put("msg", ex.getMessage());
            return createExceptionResponse(ex, headers, status, request, "This HTTP method is not supported", causes);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PropertyReferenceException.class)
    protected ResponseEntity<Object> handlePropertyReference(PropertyReferenceException ex, WebRequest request) {
        Map<String, Object> causes = new HashMap<>();
        causes.put("msg", ex.getMessage());
        return createExceptionResponse(ex, null, HttpStatus.BAD_REQUEST, request, "Incorrect property", causes);
    }

    private ResponseEntity<Object> createExceptionResponse(
        Exception ex, HttpHeaders headers, HttpStatusCode status, WebRequest request, String detail, Map<String, Object> cause) {
            if (headers == null) {
                headers = new HttpHeaders();
            }
            Map<String, Object> props = new HashMap<>();
            ProblemDetail body = createProblemDetail(ex, status, detail, null, null, request);
            props.put("timestamp", System.currentTimeMillis());

            if (cause != null) {
                props.put("cause", cause);
            } else {
                Map<String, Object> causes = new HashMap<>();
                causes.put("causeMsg", ex.getCause().getMessage());
                props.put("cause", causes);
            }

            props.put("ex", ex.getClass().getSimpleName());
            
            body.setProperties(props);
            log.error(ex.getMessage());
            return handleExceptionInternal(ex, body, headers, status, request);
    }

}
