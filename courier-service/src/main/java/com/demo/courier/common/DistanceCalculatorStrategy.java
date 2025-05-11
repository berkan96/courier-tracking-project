package com.demo.courier.common;

import com.demo.courier.model.GeoLocation;

public interface DistanceCalculatorStrategy {
    double calculateDistance(GeoLocation startLocation, GeoLocation endLocation);
}
