package com.deliveryflow.delivery.Hub;

import java.util.Objects;

import com.deliveryflow.delivery.Deliveries.Address;

public final class HubMapper {

    private HubMapper() {
    }

    public static InfoAboutHub toDto(HubEntity entity) {
        Objects.requireNonNull(entity, "HubEntity must not be null");

        Address address = new Address(
            entity.getCountrycode(),
            entity.getCity(),
            entity.getStreet(),
            entity.getBuilding(),
            entity.getApartment()
        );

        return InfoAboutHub.builder()
            .hubId(entity.getId())
            .name(entity.getName())
            .type(entity.getType())
            .address(address)
            .build();
    }
}
