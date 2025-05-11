package com.demo.courier.model.dto;

import com.demo.courier.model.enums.CourierStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierDto {
    private String firstName;
    private String lastName;
    private CourierStatus status;
}
