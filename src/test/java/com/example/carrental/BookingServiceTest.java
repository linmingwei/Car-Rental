package com.example.carrental;

import com.example.carrental.entity.Booking;
import com.example.carrental.entity.Car;
import com.example.carrental.mapper.BookingMapper;
import com.example.carrental.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class BookingServiceTest {
    @Autowired
    BookingService bookingService;

    @Autowired
    BookingMapper bookingMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void testGetAvailableCarsByDate() {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.plusYears(9);
        LocalDate endDate = startDate.plusDays(9);
        List<Car> cars = bookingService.getAvailableCarsByDate(startDate, endDate);
        Assert.notEmpty(cars, "Cars should not be empty");
    }

    @Test
    public void testBookCar() {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.plusYears(9);
        LocalDate endDate = startDate.plusDays(9);
        bookingService.bookCar(1L, startDate, endDate);
    }

    @Test
    public void testGetBooking() {
        List<Booking> bookings = bookingService.getBookings(2L);
        Assert.isTrue(bookings.size()==0, "Bookings should be empty");
    }

    @Test
    public void testCancelBooking() {
        bookingService.cancelBooking(1L);
        Assert.isTrue(bookingMapper.getBookings(Booking.builder().id(1L).build()).size()==0,
                "Booking should be null");
    }

}
