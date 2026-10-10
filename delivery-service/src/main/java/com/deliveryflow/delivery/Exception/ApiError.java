package com.deliveryflow.delivery.Exception;

import java.time.Instant;
import java.util.List;

public record ApiError(
        Instant timestamp,
        int status,
        String code,
        String message,
        String path,
        List<ValidationError> validationErrors,
        String traceId
) {
}    

