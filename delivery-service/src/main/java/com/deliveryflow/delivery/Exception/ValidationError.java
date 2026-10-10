package com.deliveryflow.delivery.Exception;

public record ValidationError(
        String field,
        String message
) {
}