package com.deliveryflow.delivery.Deliveries;

import java.time.OffsetDateTime;
import java.util.UUID;


import lombok.Builder;

@Builder 
public record DeliveryResponse(
    UUID deliveryId,
    Long orderId,
    Long sellerOrderId,
    Long sellerId,
    DeliveryStatus status,
    UUID originHubId,
    UUID destinationHubId,
    OffsetDateTime acceptedAt,
    String acceptedBy,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt
) {
    
}
