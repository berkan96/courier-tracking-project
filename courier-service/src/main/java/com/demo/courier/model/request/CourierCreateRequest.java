package com.demo.courier.model.request;

import jakarta.validation.constraints.NotNull;

public record CourierCreateRequest(String identityNo, @NotNull String firstName, @NotNull String lastName) {
}
