package com.demo.courier.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class CourierTotalDistanceResponse {
    private Double totalDistance;
}
