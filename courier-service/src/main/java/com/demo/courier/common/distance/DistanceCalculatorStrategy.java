package com.demo.courier.common.distance;

import com.demo.courier.model.GeoLocation;

public interface DistanceCalculatorStrategy {
    double calculateDistance(GeoLocation startLocation, GeoLocation endLocation);
}
