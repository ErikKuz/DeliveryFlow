package com.deliveryflow.delivery.Exception;

public class NotFoundDeliveryOrderByIdException extends RuntimeException{
    public NotFoundDeliveryOrderByIdException(String message){
        super(message);
    }
}
