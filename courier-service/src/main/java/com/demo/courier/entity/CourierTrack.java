package com.demo.courier.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courier_tracks", indexes = {
        @Index(name = "idx_courier_track_on_courier_id_and_createdDate", columnList = "courierId, createdDate")
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
    @Column
    private LocalDateTime createdDate;
}
