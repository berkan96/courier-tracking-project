package com.demo.courier.mapper;

import com.demo.core.event.CourierLocationEvent;
import com.demo.core.model.GeoLocation;
import com.demo.courier.entity.CourierTrack;
import com.demo.courier.model.request.CourierLocationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourierTrackMapper {
    CourierTrack locationRequestToCourierTrack(CourierLocationRequest request);

    GeoLocation courierTrackToGeoLocation(CourierTrack courierTrack);

    CourierLocationEvent courierTrackToCourierLocationEvent(CourierTrack courierTrack);
}
