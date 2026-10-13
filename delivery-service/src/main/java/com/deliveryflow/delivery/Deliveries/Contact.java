package com.deliveryflow.delivery.Deliveries;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record Contact(
    @NotBlank @Size(max = 120) String name,
    @NotBlank @Pattern(regexp = "^\\+[1-9]\\d{1,14}$") String phone
){}
