package com.deliveryflow.delivery.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deliveryflow.delivery.Deliveries.DeliveriesEntity;

@Repository 
public interface DeliveriesRepository extends JpaRepository<DeliveriesEntity,java.util.UUID>{
    
    java.util.Optional<DeliveriesEntity> findBySellerOrderId(Long sellerOrderId);
    
}
