package com.demo.courier.service;

import com.demo.core.distance.DistanceCalculatorFactory;
import com.demo.core.distance.DistanceCalculatorStrategy;
import com.demo.core.enums.DistanceType;
import com.demo.core.model.GeoLocation;
import com.demo.courier.entity.CourierTrack;
import com.demo.courier.mapper.CourierTrackMapper;
import com.demo.courier.model.request.CourierLocationRequest;
import com.demo.courier.model.request.CourierTotalDistanceRequest;
import com.demo.courier.model.response.CourierTotalDistanceResponse;
import com.demo.courier.repository.CourierTrackRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@AllArgsConstructor
@Service
public class CourierTrackService {
    private final CourierTrackRepository courierTrackRepository;
    private final CourierTrackMapper courierTrackMapper;

    public void saveCourierTrack(CourierLocationRequest request) {
        CourierTrack courierTrack = courierTrackMapper.locationRequestToCourierTrack(request);
        courierTrackRepository.save(courierTrack);
    }

    public CourierTotalDistanceResponse getTotalDistance(CourierTotalDistanceRequest request) {
        List<CourierTrack> courierTrackList = getTrackList(request);

        double totalDistance = IntStream.range(1, courierTrackList.size())
                .mapToDouble(i -> {
                    CourierTrack previous = courierTrackList.get(i - 1);
                    CourierTrack current = courierTrackList.get(i);
                    return calculateDistance(previous, current);
                })
                .sum();

        return CourierTotalDistanceResponse.builder()
                .totalDistance(totalDistance)
                .build();
    }

    private List<CourierTrack> getTrackList(CourierTotalDistanceRequest request) {
        Specification<CourierTrack> specification = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("courierId"), request.getCourierId());

        if (request.getStartDate() != null) {
            specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("createdDate"), request.getStartDate()));
        }

        if (request.getEndDate() != null) {
            specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate"), request.getEndDate()));
        }

        return courierTrackRepository.findAll(specification);
    }

    private Double calculateDistance(CourierTrack previous, CourierTrack current) {
        GeoLocation previousLocation = courierTrackMapper.courierTrackToGeoLocation(previous);
        GeoLocation currentLocation = courierTrackMapper.courierTrackToGeoLocation(current);
        DistanceCalculatorStrategy strategy = DistanceCalculatorFactory.getInstance().getCalculator(DistanceType.HAVERSINE);
        return strategy.calculateDistance(previousLocation, currentLocation);
    }
}
