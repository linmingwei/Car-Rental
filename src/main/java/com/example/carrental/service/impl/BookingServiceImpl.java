package com.example.carrental.service.impl;

import com.example.carrental.entity.Booking;
import com.example.carrental.entity.Car;
import com.example.carrental.entity.Customer;
import com.example.carrental.mapper.BookingMapper;
import com.example.carrental.service.BookingService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Setter(onMethod_ = {@Autowired})
@Slf4j
public class BookingServiceImpl implements BookingService {
    private BookingMapper bookingMapper;

    @Override
    public List<Car> getAvailableCarsByDate(LocalDate startDate, LocalDate endDate) {
        log.info("Get available cars by date {} - {}", startDate, endDate);
        return bookingMapper.getAvailableCarsByDate(startDate, endDate);
    }


    @Override
    @Transactional
    public synchronized Booking bookCar(Long carId, LocalDate startDate, LocalDate endDate) {
        Car car = bookingMapper.getCarById(carId);
        if (Objects.isNull(car)) {
            throw new IllegalArgumentException("Car with id " + carId + " does not exist");
        }
        Customer customer = getCurrentCustomer();
        Booking entity = Booking.builder()
                .carId(carId)
                .customerId(customer.getId())
                .startDate(startDate)
                .endDate(endDate)
                .build();
        if(!CollectionUtils.isEmpty( bookingMapper.getBookings(entity))){
            throw new IllegalArgumentException("The car " + car.getCarModel() + " is already booked");
        }
        Booking booking = Booking.builder()
                .carId(car.getId())
                .carModel(car.getCarModel())
                .customerId(customer.getId())
                .customerName(customer.getUsername())
                .startDate(startDate)
                .endDate(endDate)
                .totalRent(car.getRentPrice() * (Period.between(startDate, endDate).getDays()+1))
                .status(1)
                .build();
        bookingMapper.bookCar(booking);
        log.info("Book car {} for customer {} success.", car.getCarModel(), customer.getUsername());
        return booking;
    }

    @Override
    public void cancelBooking(Long bookingId) {
        log.info("Cancel booking with id {}", bookingId);
        bookingMapper.cancelBooking(bookingId);

    }

    @Override
    public List<Booking> getBookings(Long customerId) {
        log.info("Get bookings for customer with id {}", customerId);
        Booking booking = Booking.builder().customerId(customerId).build();
        return bookingMapper.getBookings(booking);
    }

    private Customer getCurrentCustomer() {
        return Customer.builder()
                .id(1L)
                .username("admin")
                .build();
    }
}
