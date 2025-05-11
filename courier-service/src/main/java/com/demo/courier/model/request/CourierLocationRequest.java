package com.demo.courier.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourierLocationRequest {
    @NotNull
    private Long courierId;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;

}
