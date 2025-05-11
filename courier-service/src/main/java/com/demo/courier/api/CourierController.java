package com.demo.courier.api;

import com.demo.courier.model.dto.CourierDto;
import com.demo.courier.model.enums.CourierStatus;
import com.demo.courier.model.request.CourierCreateRequest;
import com.demo.courier.model.request.CourierLocationRequest;
import com.demo.courier.service.CourierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("courier")
@RequiredArgsConstructor
public class CourierController {
    private CourierService courierService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCourier(@Valid @RequestBody CourierCreateRequest request) {
        courierService.createCourier(request);
    }

    @PostMapping("/save-location")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveCurrentLocation(@Valid @RequestBody CourierLocationRequest request) {
        courierService.saveCurrentLocation(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourierDto> getCourier(@PathVariable("id") Long id) {
        return ResponseEntity.ok(courierService.getCourier(id));
    }

    @PatchMapping("/{id}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable("id") Long id, @RequestParam("status") CourierStatus status) {
        courierService.updateCourierStatus(id, status);
    }
}
