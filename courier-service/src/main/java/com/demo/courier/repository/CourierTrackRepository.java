package com.demo.courier.repository;

import com.demo.courier.entity.CourierTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CourierTrackRepository extends JpaRepository<CourierTrack, Long>, JpaSpecificationExecutor<CourierTrack> {
}
