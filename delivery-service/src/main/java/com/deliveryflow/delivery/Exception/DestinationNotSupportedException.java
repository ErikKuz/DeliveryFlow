package com.deliveryflow.delivery.Exception;

public class DestinationNotSupportedException extends RuntimeException {
    public DestinationNotSupportedException(String city) {
        super("Для города «" + city + "» нет обслуживаемого пункта.");
    }
}
