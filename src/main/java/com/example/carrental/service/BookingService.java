package com.example.carrental.service;

import com.example.carrental.entity.Booking;
import com.example.carrental.entity.Car;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    /**
     * Get available cars for given period.
     * @param startDate start date of period.
     * @param endDate end date of period.
     * @return list of available cars.
     */
    List<Car> getAvailableCarsByDate(LocalDate startDate, LocalDate endDate);


    /**
     * Create new booking.
     * @param carId car id.
     * @param startDate start date of period.
     * @param endDate end date of period.
     */
    Booking bookCar(Long carId, LocalDate startDate, LocalDate endDate);

    /**
     * Cancel booking.
     * @param bookingId booking id.
     */
    void cancelBooking(Long bookingId);

    /**
     * Get bookings by customer id.
     * @param customerId customer id.
     * @return list of bookings.
     */
    List<Booking> getBookings(Long customerId);
}
