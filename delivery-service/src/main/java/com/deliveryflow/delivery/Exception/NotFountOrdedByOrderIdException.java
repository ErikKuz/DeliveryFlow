package com.deliveryflow.delivery.Exception;

public class NotFountOrdedByOrderIdException extends RuntimeException{
    public NotFountOrdedByOrderIdException(String message){
        super(message);
    }
}
