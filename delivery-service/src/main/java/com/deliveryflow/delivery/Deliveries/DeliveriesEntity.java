package com.deliveryflow.delivery.Deliveries;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "deliveries")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class DeliveriesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "seller_order_id", nullable = false, unique = true)
    private Long sellerOrderId;

    @Column(name = "seller_id", nullable = false)
    private Long sellerId;

    @Column(name = "pickup_contact_name", nullable = false, length = 120)
    private String pickupContactName;

    @Column(name = "pickup_contact_phone", nullable = false, length = 16)
    private String pickupContactPhone;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "pickup_country_code", nullable = false, length = 2, columnDefinition = "char(2)")
    private String pickupCountryCode;

    @Column(name = "pickup_city", nullable = false, length = 120)
    private String pickupCity;

    @Column(name = "pickup_street", nullable = false, length = 160)
    private String pickupStreet;

    @Column(name = "pickup_building", nullable = false, length = 30)
    private String pickupBuilding;

    @Column(name = "pickup_apartment", length = 30)
    private String pickupApartment;

    @Column(name = "recipient_name", nullable = false, length = 120)
    private String recipientName;

    @Column(name = "recipient_phone", nullable = false, length = 16)
    private String recipientPhone;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "delivery_country_code", nullable = false, length = 2, columnDefinition = "char(2)")
    private String deliveryCountryCode;

    @Column(name = "delivery_city", nullable = false, length = 120)
    private String deliveryCity;

    @Column(name = "delivery_street", nullable = false, length = 160)
    private String deliveryStreet;

    @Column(name = "delivery_building", nullable = false, length = 30)
    private String deliveryBuilding;

    @Column(name = "delivery_apartment", length = 30)
    private String deliveryApartment;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 32)
    @Builder.Default
    private DeliveryStatus status = DeliveryStatus.WAITING_FOR_ACCEPTANCE;

    @Column(name = "origin_hub_id", nullable = false)
    private UUID originHubId;

    @Column(name = "destination_hub_id", nullable = false)
    private UUID destinationHubId;

    @Column(name = "accepted_at")
    private OffsetDateTime acceptedAt;

    @Column(name = "accepted_by", length = 120)
    private String acceptedBy;

    @Column(name = "courier_id")
    private UUID courierId;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Override 
    public boolean equals(Object other){
        if (this == other) {
            return true;
        }
        if (!(other instanceof DeliveriesEntity entity)) {
            return false;
        }

        if (!Objects.equals(this.orderId, entity.getOrderId()) 
            || !Objects.equals(this.sellerOrderId, entity.getSellerOrderId()) 
            || !Objects.equals(this.sellerId, entity.getSellerId()) 
            || !Objects.equals(this.pickupContactName, entity.getPickupContactName()) 
            || !Objects.equals(this.pickupContactPhone, entity.getPickupContactPhone()) 
            || !Objects.equals(this.pickupCountryCode, entity.getPickupCountryCode()) 
            || !Objects.equals(this.pickupCity, entity.getPickupCity()) 
            || !Objects.equals(this.pickupStreet, entity.getPickupStreet()) 
            || !Objects.equals(this.pickupBuilding, entity.getPickupBuilding()) 
            || !Objects.equals(this.pickupApartment, entity.getPickupApartment()) 
            || !Objects.equals(this.recipientName, entity.getRecipientName()) 
            || !Objects.equals(this.recipientPhone, entity.getRecipientPhone())
            || !Objects.equals(this.deliveryCountryCode, entity.getDeliveryCountryCode())
            || !Objects.equals(this.deliveryCity, entity.getDeliveryCity())
            || !Objects.equals(this.deliveryStreet, entity.getDeliveryStreet())
            || !Objects.equals(this.deliveryBuilding, entity.getDeliveryBuilding())
            || !Objects.equals(this.deliveryApartment, entity.getDeliveryApartment())
        )return false;
        
        return true;
    }
}

