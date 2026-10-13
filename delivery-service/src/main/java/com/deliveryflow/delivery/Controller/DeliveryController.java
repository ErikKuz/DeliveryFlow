package com.deliveryflow.delivery.Controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryflow.delivery.Deliveries.CreateDeliveryRequest;
import com.deliveryflow.delivery.Deliveries.CreateDeliveryResponse;
import com.deliveryflow.delivery.Deliveries.DeliveryResponse;
import com.deliveryflow.delivery.Exception.InvalidOrderIdException;
import com.deliveryflow.delivery.Hub.InfoAboutHub;
import com.deliveryflow.delivery.Order.InfoAboutDeliveryOrder;
import com.deliveryflow.delivery.Order.OrderDeliveriesResponse;
import com.deliveryflow.delivery.Service.DeliveryService;

import jakarta.validation.Valid;

@RestController 
public class DeliveryController {


    private final DeliveryService DS;

    public DeliveryController (DeliveryService service){
        this.DS=service;
    }



    @PostMapping("/api/v1/deliveries")
    public ResponseEntity<DeliveryResponse> createDeliveryOrder(
        @Valid @RequestBody CreateDeliveryRequest request){
        CreateDeliveryResponse response = DS.CreateDeliveryOrder(request);
        return ResponseEntity.status(HttpStatus.valueOf(response.chois()?200:201)).body(response.response());

    }

    @GetMapping("/api/v1/deliveries/{deliveryId}")
    public ResponseEntity<InfoAboutDeliveryOrder> showPartOrder(@PathVariable UUID deliveryId){
        return (deliveryId==null)?ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        :ResponseEntity.ok().body(DS.showPartOrder(deliveryId));
    }

    @GetMapping("/api/v1/hubs")
    public ResponseEntity<java.util.List<InfoAboutHub>> showAllHubs(){
        return ResponseEntity.ok().body(DS.showAllHubs());
    }

    @GetMapping("/api/v1/orders/{orderId}/deliveries")
    public ResponseEntity<OrderDeliveriesResponse>
        showOrder(@PathVariable("orderId") Long orderId){
        if (orderId == null || orderId <= 0) {
            throw new InvalidOrderIdException();
        }
        return ResponseEntity.ok().body(DS.showOrder(orderId));
    }
}
