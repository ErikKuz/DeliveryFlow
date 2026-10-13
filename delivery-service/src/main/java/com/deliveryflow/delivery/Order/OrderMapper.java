package com.deliveryflow.delivery.Order;

import com.deliveryflow.delivery.Deliveries.Address;
import com.deliveryflow.delivery.Deliveries.Contact;
import com.deliveryflow.delivery.Deliveries.DeliveriesEntity;
import com.deliveryflow.delivery.Hub.HubEntity;
import com.deliveryflow.delivery.Hub.HubMapper;
import com.deliveryflow.delivery.InfoAboutReceipent;

public final class OrderMapper {

    private OrderMapper() {
    }

    public static InfoAboutDeliveryOrder toDto(
        DeliveriesEntity entity,
        HubEntity mainHub,
        HubEntity destinationHub
    ) {

        Address deliveryAddress = new Address(
            entity.getDeliveryCountryCode(),
            entity.getDeliveryCity(),
            entity.getDeliveryStreet(),
            entity.getDeliveryBuilding(),
            entity.getDeliveryApartment()
        );
        Contact deliverycontact = new Contact(
            entity.getRecipientName(),
            entity.getRecipientPhone()
        );

        return InfoAboutDeliveryOrder.builder()
            .deliveryId(entity.getId())
            .orderId(entity.getOrderId())
            .sellerOrderId(entity.getSellerOrderId())
            .status(entity.getStatus())
            .AddressMainHub(HubMapper.toDto(mainHub))
            .AddressdestinationHub(HubMapper.toDto(destinationHub))
            .receipent(InfoAboutReceipent.builder()
                .address(deliveryAddress)
                .contact(deliverycontact)
                .build())
            .build();
    }
}
