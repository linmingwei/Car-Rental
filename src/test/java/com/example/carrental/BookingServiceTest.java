package com.example.carrental;

import com.example.carrental.entity.Booking;
import com.example.carrental.entity.Car;
import com.example.carrental.mapper.BookingMapper;
import com.example.carrental.service.BookingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@SpringBootTest
class BookingServiceTest {
    @Autowired
    private BookingService bookingService;

    @MockBean
    private BookingMapper bookingMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void testGetAvailableCarsByDate() {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.plusYears(9);
        LocalDate endDate = startDate.plusDays(9);
        Mockito.when(bookingMapper.getAvailableCarsByDate(startDate, endDate))
                .thenReturn(Collections.singletonList(buildCar()));
        List<Car> cars = bookingService.getAvailableCarsByDate(startDate, endDate);
        Assertions.assertTrue(cars != null && cars.size() > 0, "Cars should not be empty");
    }

    @Test
    public void testBookCar() {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.plusYears(9);
        LocalDate endDate = startDate.plusDays(9);
        Mockito.when(bookingMapper.getCarById(1L))
                .thenReturn(buildCar());
        Mockito.when(bookingMapper.getBookings(Booking.builder().carId(1L).build()))
                .thenReturn(null);
        Booking booking = buildBooking(startDate, endDate);
        bookingMapper.bookCar(booking);
        Booking res = bookingService.bookCar(1L, startDate, endDate);
        Assertions.assertNotNull(res, "Booking result should not be null");
    }

    @Test
    public void testBookCar_CarNotAvailable() {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.plusYears(9);
        LocalDate endDate = startDate.plusDays(9);
        Mockito.when(bookingMapper.getCarById(1L))
                .thenReturn(null);
        Mockito.when(bookingMapper.getBookings(Booking.builder().carId(1L).build()))
                .thenReturn(null);
        Booking booking = buildBooking(startDate, endDate);
        bookingMapper.bookCar(booking);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bookingService.bookCar(1L, startDate, endDate);
        }, "Car with id 1 does not exist");
    }

    @Test
    public void testBookCar_CarAlreadyBooked() {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.plusYears(9);
        LocalDate endDate = startDate.plusDays(9);
        Mockito.when(bookingMapper.getCarById(1L))
                .thenReturn(buildCar());
        Mockito.when(bookingMapper.getBookings(Booking.builder().carId(1L).customerId(1L)
                        .startDate(startDate)
                        .endDate(endDate)
                        .build()))
                .thenReturn(Collections.singletonList(buildBooking(startDate, endDate)));
        Booking booking = buildBooking(startDate, endDate);
        bookingMapper.bookCar(booking);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            bookingService.bookCar(1L, startDate, endDate);
        }, "Car with id 1 is already booked");
    }

    private Booking buildBooking(LocalDate startDate, LocalDate endDate) {
        return Booking.builder()
                .id(1L)
                .carId(1L)
                .customerId(1L)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }

    private Car buildCar() {
        return Car.builder().id(1L).rentPrice(50).build();
    }

}
