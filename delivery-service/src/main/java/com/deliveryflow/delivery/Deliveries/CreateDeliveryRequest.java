package com.deliveryflow.delivery.Deliveries;

import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateDeliveryRequest(
    @NotNull
    @Positive
    Long orderId,
    @NotNull
    @Positive
    Long sellerOrderId,
    @NotNull
    @Positive
    Long sellerId,
    @NotNull
    @Valid
    Contact pickupContact,
    @NotNull
    @Valid
    Address pickupAddress,
    @NotNull
    @Valid
    Contact recipient,
    @NotNull
    @Valid
    Address deliveryAddress

){}
