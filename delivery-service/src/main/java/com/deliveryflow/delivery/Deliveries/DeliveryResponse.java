package com.deliveryflow.delivery.Deliveries;

import java.time.OffsetDateTime;
import java.util.UUID;


import lombok.Builder;

@Builder 
public record DeliveryResponse(
    UUID deliveryId,
    Long orderId,
    Long sellerOrderId,
    DeliveryStatus status,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {
    
}
