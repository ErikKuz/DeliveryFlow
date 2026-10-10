package com.deliveryflow.delivery.Deliveries;


public class DeliveryMapper {
    public static DeliveryResponse EntityTransformInResponse(DeliveriesEntity entity){
        return DeliveryResponse.builder()
            .deliveryId(entity.getId())
            .orderId(entity.getOrderId())
            .sellerOrderId(entity.getSellerOrderId())
            .status(entity.getStatus())
            .createdAt(entity.getCreatedAt())
            .updatedAt(entity.getUpdatedAt())
            .build();

    }

    public static DeliveriesEntity RequestTransformInEntity(CreateDeliveryRequest request){
        return DeliveriesEntity.builder()
            .orderId(request.orderId())
            .sellerOrderId(request.sellerOrderId())
            .sellerId(request.sellerId())
            .pickupContactName(request.pickupContact().name())
            .pickupContactPhone(request.pickupContact().phone())
            .pickupCountryCode(request.pickupAddress().countryCode())
            .pickupCity(request.pickupAddress().city())
            .pickupStreet(request.pickupAddress().street())
            .pickupBuilding(request.pickupAddress().building())
            .pickupApartment(request.pickupAddress().apartment())
            .recipientName(request.recipient().name())
            .recipientPhone(request.recipient().phone())
            .deliveryCountryCode(request.deliveryAddress().countryCode())
            .deliveryCity(request.deliveryAddress().city())
            .deliveryStreet(request.deliveryAddress().street())
            .deliveryBuilding(request.deliveryAddress().building())
            .deliveryApartment(request.deliveryAddress().apartment())

            .build();

    }
}
