package com.example.carrental.entity;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
public class Booking {

    private Long id;
    private Long customerId;
    private String customerName;
    private Long carId;
    private String carModel;
    private LocalDate startDate;
    private LocalDate endDate;
    //todo totalRent type
    private Integer totalRent;
    private int status;
}
