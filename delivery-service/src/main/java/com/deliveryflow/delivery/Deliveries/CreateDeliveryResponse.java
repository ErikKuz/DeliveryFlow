package com.deliveryflow.delivery.Deliveries;

import lombok.Builder;

@Builder 
public record CreateDeliveryResponse(DeliveryResponse response,Boolean chois){
    
}
