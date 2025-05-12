package com.demo.core.distance;


import com.demo.core.model.GeoLocation;

public interface DistanceCalculatorStrategy {
    double calculateDistance(GeoLocation startLocation, GeoLocation endLocation);
}
