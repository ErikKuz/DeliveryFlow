package com.deliveryflow.delivery.Hub;

import java.util.UUID;

import com.deliveryflow.delivery.Deliveries.Address;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record InfoAboutHub(
    @NotNull UUID hubId,
    @NotBlank @Size(max = 120) String name,
    @NotBlank @Pattern(regexp = "MAIN_MOSCOW|REGIONAL") String type,
    @NotNull @Valid Address address
) {
}
