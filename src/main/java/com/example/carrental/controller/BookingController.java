package com.example.carrental.controller;

import com.example.carrental.common.RestResponse;
import com.example.carrental.dto.BookingRequest;
import com.example.carrental.entity.Car;
import com.example.carrental.service.BookingService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1")
@Setter(onMethod_ = {@Autowired})
@Slf4j
@Validated
public class BookingController {
    private BookingService bookingService;

    @GetMapping("/cars")
    public RestResponse getAvailableCarsByDate(@NotNull LocalDate startDate, @NotNull LocalDate endDate){
        log.info("Get available cars from date {} to {}.", startDate, endDate);
        LocalDate today = LocalDate.now();
        if(startDate.isBefore(today) || endDate.isBefore(today) || startDate.isAfter(endDate)){
            throw new IllegalArgumentException("Invalid booking car date range.");
        }
        return RestResponse.success(bookingService. getAvailableCarsByDate(startDate, endDate));
    }

    @PostMapping("/bookings")
    public RestResponse bookCar(@RequestBody @Validated BookingRequest request){
        log.info("Book car with id {} from date {} to {}.", request.getCarId(), request.getStartDate(), request.getEndDate());
        return RestResponse.success(bookingService.bookCar(request.getCarId(), request.getStartDate(), request.getEndDate()));
    }

    @DeleteMapping("/bookings/{bookingId}")
    public RestResponse cancelBooking(@PathVariable Long bookingId){
        log.info("Cancel booking with id {}.", bookingId);
        bookingService.cancelBooking(bookingId);
        return RestResponse.success();
    }

    @GetMapping("/bookings")
    public RestResponse getBookings(@RequestParam("customerId") Long customerId){
        return RestResponse.success(bookingService.getBookings(customerId));
    }






}
