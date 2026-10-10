package com.deliveryflow.delivery.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deliveryflow.delivery.Deliveries.CreateDeliveryRequest;
import com.deliveryflow.delivery.Deliveries.CreateDeliveryResponse;
import com.deliveryflow.delivery.Deliveries.DeliveryResponse;
import com.deliveryflow.delivery.Service.DeliveryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController 
@RequiredArgsConstructor 
public class DeliveryController {
    private final DeliveryService DS;

    @PostMapping("/api/v1/deliveries")
    public ResponseEntity<DeliveryResponse> createDeliveryOrder(
        @Valid @RequestBody CreateDeliveryRequest request){
        CreateDeliveryResponse response = DS.CreateDeliveryOrder(request);
        return ResponseEntity.status(HttpStatus.valueOf(response.chois()?200:201)).body(response.response());

    }
}
