package com.demo.store.client;

import com.demo.store.model.dto.CourierDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${feign.client.config.courier-service.name}", url = "${feign.client.config.courier-service.url}")
public interface CourierServiceClient {
    @GetMapping("/couriers/{id}")
    ResponseEntity<CourierDto> getCourier(@PathVariable("id") Long id);

}
