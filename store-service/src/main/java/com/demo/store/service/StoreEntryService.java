package com.demo.store.service;

import com.demo.core.distance.DistanceCalculatorFactory;
import com.demo.core.distance.DistanceCalculatorStrategy;
import com.demo.core.enums.DistanceType;
import com.demo.core.event.CourierLocationEvent;
import com.demo.core.model.GeoLocation;
import com.demo.store.constant.StoreServiceConstants;
import com.demo.store.document.StoreEntry;
import com.demo.store.model.dto.StoreDto;
import com.demo.store.repository.StoreEntryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.demo.store.constant.StoreServiceConstants.MINUTES;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreEntryService {
    private final StoreService storeService;
    private final StoreEntryRepository storeEntryLogRepository;

    public Optional<StoreEntry> getLastEntryRecord(Long storeId) {
        return storeEntryLogRepository.findFirstByStoreIdOrderByCreatedDateDesc(storeId);
    }

    public void courierForStore(CourierLocationEvent event) {
        storeService.getAllStores()
                .stream()
                .filter(store -> isWithin100Meters(event, store) && shouldLogEntry(store.getId(), event.getCreatedDate()))
                .findFirst()
                .ifPresent(store -> saveStoreEntry(store.getId(), event.getCourierId()));
    }
    public void saveStoreEntry(Long storeId, Long courierId) {
        StoreEntry storeEntry = new StoreEntry();
        storeEntry.setStoreId(storeId);
        storeEntry.setCourierId(courierId);
        storeEntry.setPickupDate(LocalDateTime.now());
        storeEntryLogRepository.save(storeEntry);
        log.info("Saved store log entry log");
    }
    private double getDistance(CourierLocationEvent event, StoreDto store) {
        GeoLocation previousLocation = GeoLocation.builder()
                .latitude(event.getLatitude())
                .longitude(event.getLongitude())
                .build();
        GeoLocation currentLocation = GeoLocation.builder()
                .latitude(store.getLatitude())
                .longitude(store.getLongitude())
                .build();
        DistanceCalculatorStrategy strategy = DistanceCalculatorFactory.getInstance().getCalculator(DistanceType.HAVERSINE);
        return strategy.calculateDistance(previousLocation, currentLocation);
    }

    private boolean isWithin100Meters(CourierLocationEvent event, StoreDto store) {

        double distance = getDistance(event, store);
        return distance <= StoreServiceConstants.RADIUS;
    }

    private boolean shouldLogEntry(Long storeId, LocalDateTime timestamp) {
        return getLastEntryRecord(storeId)
                .map(lastEntry -> Duration.between(lastEntry.getPickupDate(), timestamp).toMinutes() > MINUTES)
                .orElse(true);
    }
}
