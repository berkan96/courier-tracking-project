package com.demo.courier.model.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CourierTotalDistanceRequest {
    private Long courierId;
    private LocalDate startDate;
    private LocalDate endDate;
}
