package com.demo.courier.api;

import com.demo.courier.model.request.CourierTotalDistanceRequest;
import com.demo.courier.model.response.CourierTotalDistanceResponse;
import com.demo.courier.service.CourierTrackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/couriers-track")
@RestController
public class CourierTrackController {

    private final CourierTrackService courierTrackService;

    @PostMapping("/total-distance")
    public ResponseEntity<CourierTotalDistanceResponse> getTotalDistance(@Valid @RequestBody CourierTotalDistanceRequest request) {
        return ResponseEntity.ok(courierTrackService.getTotalDistance(request));
    }
}
