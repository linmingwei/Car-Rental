package com.example.carrental.mapper;

import com.example.carrental.entity.Booking;
import com.example.carrental.entity.Car;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface BookingMapper {

    List<Car> getAvailableCarsByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    void bookCar(@Param("booking") Booking booking);

    Car getCarById(@Param("carId") Long carId);

    void cancelBooking(@Param("bookingId") Long bookingId);

    List<Booking> getBookings(@Param("booking") Booking booking);
}
