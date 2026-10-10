package com.deliveryflow.delivery.Exception;

import java.time.Instant;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice(
    basePackages = "com.deliveryflow.delivery.Controller"
)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalRestExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
        HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
            Instant.now(),
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST.getReasonPhrase(),
            "Validation failed for one or more fields",
            request.getRequestURI(),
            ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ValidationError(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                ))
                .toList(),
            null
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
        HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
            Instant.now(),
            HttpStatus.BAD_REQUEST.value(),
            "INVALID_JSON",
            "Request body is missing or contains invalid JSON or field types",
            request.getRequestURI(),
            null,
            null
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
        DataAccessResourceFailureException.class,
        TransientDataAccessResourceException.class,
        QueryTimeoutException.class,
        CannotCreateTransactionException.class
    })
    public ResponseEntity<ApiError> handleServiceUnavailable(Exception ex,
        HttpServletRequest request
    ) {
        ApiError apiError = new ApiError(
            Instant.now(),
            HttpStatus.SERVICE_UNAVAILABLE.value(),
            "SERVICE_UNAVAILABLE",
            "Delivery service is temporarily unable to accept the request",
            request.getRequestURI(),
            null,
            null
        );
        return new ResponseEntity<>(apiError, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(UnCorrectDeliveriesInfoException.class)
    public ResponseEntity<ApiError> handleUnCorrectDeliveriesInfoException(UnCorrectDeliveriesInfoException ex,
        HttpServletRequest request
    ){
        ApiError apiError = new ApiError(
            Instant.now(),
            HttpStatus.valueOf(409).value(),
            HttpStatus.valueOf(409).getReasonPhrase(),
            ex.getMessage(),
            request.getRequestURI(),
            null,
            null
        );
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(409));
    }
}
