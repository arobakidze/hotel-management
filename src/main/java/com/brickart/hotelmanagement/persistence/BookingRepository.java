package com.brickart.hotelmanagement.persistence;

import com.brickart.hotelmanagement.domain.Booking;

import java.util.List;

public interface BookingRepository {
    void create(Booking booking);

    void update(Booking booking);

    void delete(Long id);

    Booking findById(Long id);

    List<Booking> findAll();

    List<Booking> findAllWithDetails();
}