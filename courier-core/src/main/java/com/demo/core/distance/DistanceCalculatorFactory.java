package com.demo.core.distance;


import com.demo.core.enums.DistanceType;

import java.util.Map;
import java.util.Objects;

public class DistanceCalculatorFactory {
    private static volatile DistanceCalculatorFactory instance;
    private static final Map<DistanceType, DistanceCalculatorStrategy> STRATEGY_MAP = Map.of(
            DistanceType.HAVERSINE, new HaversineCalculatorStrategy()
    );

    private DistanceCalculatorFactory() {
    }

    public static DistanceCalculatorFactory getInstance() {
        if (instance == null) {
            synchronized (DistanceCalculatorFactory.class) {
                if (instance == null) {
                    instance = new DistanceCalculatorFactory();
                }
            }
        }
        return instance;
    }

    public DistanceCalculatorStrategy getCalculator(DistanceType type) {
        DistanceCalculatorStrategy strategy = STRATEGY_MAP.get(Objects.requireNonNull(type));
        if (strategy == null) {
            throw new IllegalArgumentException("Unknown Distance Type");
        }
        return strategy;
    }
}
