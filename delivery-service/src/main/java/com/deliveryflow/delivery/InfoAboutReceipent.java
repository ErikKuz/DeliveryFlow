package com.deliveryflow.delivery;

import com.deliveryflow.delivery.Deliveries.Address;
import com.deliveryflow.delivery.Deliveries.Contact;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record InfoAboutReceipent(
    @NotBlank @Valid Address address,
    @NotBlank @Valid Contact contact
) {
}
