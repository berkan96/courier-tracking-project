package com.demo.courier.model.request;

import jakarta.validation.constraints.NotNull;


public record CourierLocationRequest(@NotNull Long courierId, @NotNull Double latitude , @NotNull Double longitude) {
}
