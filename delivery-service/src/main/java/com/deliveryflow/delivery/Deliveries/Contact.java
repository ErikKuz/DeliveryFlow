package com.deliveryflow.delivery.Deliveries;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record Contact(
    @NotNull @Size(min = 1, max = 120) String name,
    @NotNull @Pattern(regexp = "^\\+[1-9]\\d{1,14}$") String phone
){}
