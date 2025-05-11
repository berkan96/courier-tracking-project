package com.demo.courier.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Table(name = "courier_tracks", indexes = {
        @Index(name = "idx_courier_track_on_courier_id_and_updated_date", columnList = "courierId, updatedDate")
})
@EntityListeners(AuditingEntityListener.class)
public class CourierTrack {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long courierId;

    private Double latitude;

    private Double longitude;

    @CreatedDate
    private LocalDateTime createdDate;
}
