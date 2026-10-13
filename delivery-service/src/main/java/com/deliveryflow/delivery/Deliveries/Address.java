package com.deliveryflow.delivery.Deliveries;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public record Address(
    @NotNull @Pattern(regexp = "RU") String countryCode,
    @NotBlank @Size(max = 120) String city,
    @NotBlank @Size(max = 160) String street,
    @NotBlank @Size(max = 30) String building,
    @Size(max = 30) String apartment){}
