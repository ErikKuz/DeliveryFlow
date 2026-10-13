package com.deliveryflow.delivery.Hub;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name="hubs")
public class HubEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private java.util.UUID id;

    @Column(nullable=false,length=120)
    private String name;

    @Column(nullable=false,length=16)
    private String type;

    @Column(name="country_code",nullable=false,length=2)
    private String countrycode;

    @Column(nullable=false,length=120)
    private String city;

    @Column(nullable=false,length=160)
    private String street;

    @Column (nullable=false,length=30)
    private String building;

    @Column(nullable=true,length=30)
    private String apartment;
}
