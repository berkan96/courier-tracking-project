package com.demo.courier.mapper;

import com.demo.courier.entity.Courier;
import com.demo.courier.model.dto.CourierDto;
import com.demo.courier.model.request.CourierCreateRequest;
import org.mapstruct.Mapper;

@Mapper
public interface CourierMapper {
    Courier createCourierRequestToCourier(CourierCreateRequest request);

    CourierDto courierToCourierDto(Courier courier);
}
