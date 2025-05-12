package com.demo.core.event;

import com.demo.core.enums.CourierStatus;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourierLocationEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long courierId;
    private Double latitude;
    private Double longitude;
    private CourierStatus status;
    private LocalDateTime createdDate;
}
