package com.brickart.hotelmanagement.service;

import com.brickart.hotelmanagement.domain.Booking;

import java.util.List;

public interface BookingService {
    Booking create(Booking booking);

    void update(Booking booking);

    void delete(Long id);

    List<Booking> findAll();

    List<Booking> findAllWithDetails();
}