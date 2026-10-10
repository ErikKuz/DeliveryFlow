package com.deliveryflow.delivery.Deliveries;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public record Address(
    @NotNull @Pattern(regexp = "^[A-Z]{2}$") String countryCode,
    @NotNull @Size(min = 1, max = 120) String city,
    @NotNull @Size(min = 1, max = 160) String street,
    @NotNull @Size(min = 1, max = 30) String building,
    @Size(max = 30) String apartment){}
