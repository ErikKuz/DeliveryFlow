package com.deliveryflow.delivery.Exception;

public class InvalidOrderIdException extends RuntimeException {
    public InvalidOrderIdException() {
        super("orderId должен быть положительным числом в диапазоне int64.");
    }
}
