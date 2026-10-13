package com.deliveryflow.delivery.Order;

import java.util.UUID;

import com.deliveryflow.delivery.Deliveries.DeliveryStatus;
import com.deliveryflow.delivery.Hub.InfoAboutHub;

import lombok.Builder;

import com.deliveryflow.delivery.InfoAboutReceipent;

@Builder
public record InfoAboutDeliveryOrder(
    UUID deliveryId,
    Long orderId,
    Long sellerOrderId,
    DeliveryStatus status,
    InfoAboutHub AddressMainHub,
    InfoAboutHub AddressdestinationHub,
    InfoAboutReceipent receipent

) {

}
