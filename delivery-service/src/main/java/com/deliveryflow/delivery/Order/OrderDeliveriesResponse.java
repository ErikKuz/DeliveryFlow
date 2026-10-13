package com.deliveryflow.delivery.Order;

import java.util.List;

import com.deliveryflow.delivery.Deliveries.DeliveryResponse;

public record OrderDeliveriesResponse(Long orderId, List<DeliveryResponse> items) {
}
