package com.deliveryflow.delivery.Deliveries;

public enum DeliveryStatus {
    CREATED,
    WAITING_FOR_COURIER,
    COURIER_ASSIGNED,
    PICKED_UP,
    IN_TRANSIT,
    DELIVERED,
    ASSIGNMENT_FAILED,
    DELIVERY_FAILED,
    CANCELLED
}

