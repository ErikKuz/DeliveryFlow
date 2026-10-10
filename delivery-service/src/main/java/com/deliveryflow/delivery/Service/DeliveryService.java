package com.deliveryflow.delivery.Service;

import org.springframework.stereotype.Service;

import com.deliveryflow.delivery.Deliveries.CreateDeliveryRequest;
import com.deliveryflow.delivery.Deliveries.CreateDeliveryResponse;
import com.deliveryflow.delivery.Deliveries.DeliveriesEntity;
import com.deliveryflow.delivery.Deliveries.DeliveryMapper;
import com.deliveryflow.delivery.Exception.UnCorrectDeliveriesInfoException;
import com.deliveryflow.delivery.Repository.DeliveriesRepository;

import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor 
@Service
public class DeliveryService {
    private final DeliveriesRepository DR;

    public CreateDeliveryResponse CreateDeliveryOrder(CreateDeliveryRequest request){
        if (DR.findBySellerOrderId(request.sellerOrderId()).isPresent()){
            DeliveriesEntity entity=DR.findBySellerOrderId(request.sellerOrderId()).get();
            if (entity.equals(DeliveryMapper.RequestTransformInEntity(request))){
                return CreateDeliveryResponse.builder().response(DeliveryMapper.EntityTransformInResponse(entity))
                    .chois(true).build();
            }
            else{throw new UnCorrectDeliveriesInfoException("Delivery with this ID already exists, but the information does not match the existing record.");}
        }
        DeliveriesEntity entity=DR.save(DeliveryMapper.RequestTransformInEntity(request));
        return CreateDeliveryResponse.builder()
            .response(DeliveryMapper.EntityTransformInResponse(entity)).chois(false).build();
    }
}
