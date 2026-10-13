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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
            "VALIDATION_FAILED",
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

    @ExceptionHandler(InvalidOrderIdException.class)
    public ResponseEntity<ApiError> handleInvalidOrderId(InvalidOrderIdException ex,
        HttpServletRequest request
    ) {
        return error(HttpStatus.BAD_REQUEST, "INVALID_ORDER_ID", ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handlePathTypeMismatch(MethodArgumentTypeMismatchException ex,
        HttpServletRequest request
    ) {
        if ("orderId".equals(ex.getName())) {
            return error(HttpStatus.BAD_REQUEST, "INVALID_ORDER_ID",
                "orderId должен быть положительным числом в диапазоне int64.", request);
        }
        return error(HttpStatus.BAD_REQUEST, "INVALID_PATH_PARAMETER",
            "Неверный формат параметра пути.", request);
    }

    @ExceptionHandler(DestinationNotSupportedException.class)
    public ResponseEntity<ApiError> handleDestinationNotSupported(DestinationNotSupportedException ex,
        HttpServletRequest request
    ) {
        return error(HttpStatus.valueOf(422), "DESTINATION_NOT_SUPPORTED", ex.getMessage(), request);
    }

    @ExceptionHandler(HubConfigurationException.class)
    public ResponseEntity<ApiError> handleHubConfiguration(HubConfigurationException ex,
        HttpServletRequest request
    ) {
        return error(HttpStatus.SERVICE_UNAVAILABLE, "SERVICE_UNAVAILABLE", ex.getMessage(), request);
    }

    private ResponseEntity<ApiError> error(HttpStatus status, String code, String message,
        HttpServletRequest request
    ) {
        return ResponseEntity.status(status).body(new ApiError(
            Instant.now(), status.value(), code, message, request.getRequestURI(), null, null
        ));
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
            "DELIVERY_DATA_CONFLICT",
            ex.getMessage(),
            request.getRequestURI(),
            null,
            null
        );
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(409));
    }

    @ExceptionHandler(NotFoundDeliveryOrderByIdException.class)
    public ResponseEntity<ApiError> handleNotFoundDeliveryOrderByIdException
    (NotFoundDeliveryOrderByIdException ex,HttpServletRequest request){
            ApiError error = new ApiError(
                Instant.now(),
                HttpStatus.valueOf(404).value(),
                "DELIVERY_NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI(),
                null,
                null
            );
            return new ResponseEntity<>(error,HttpStatus.valueOf(404));
    }

    @ExceptionHandler(NotFountOrdedByOrderIdException.class)
    public ResponseEntity<ApiError> handleNotFountOrdedByOrderIdException(
        NotFountOrdedByOrderIdException exception,HttpServletRequest request){
            ApiError apierror =new ApiError(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                "ORDER_NOT_FOUND",
                exception.getMessage(),
                request.getRequestURI(),
                null,
                null
            );
            return new ResponseEntity<>(apierror,HttpStatus.valueOf(404));
    }
}
