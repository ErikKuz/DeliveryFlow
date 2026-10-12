package com.deliveryflow.delivery.Exception;

public class NotFoundOrderByIdException extends RuntimeException{
    public NotFoundOrderByIdException(String message){
        super(message);
    }
}
