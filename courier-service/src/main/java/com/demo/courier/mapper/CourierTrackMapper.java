package com.demo.courier.mapper;

import com.demo.core.model.GeoLocation;
import com.demo.courier.entity.CourierTrack;
import com.demo.courier.model.request.CourierLocationRequest;
import org.mapstruct.Mapper;

@Mapper
public interface CourierTrackMapper {
    CourierTrack locationRequestToCourierTrack(CourierLocationRequest request);

    GeoLocation courierTrackToGeoLocation(CourierTrack courierTrack);
}
