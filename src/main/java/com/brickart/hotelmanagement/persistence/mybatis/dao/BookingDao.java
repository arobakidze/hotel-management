package com.brickart.hotelmanagement.persistence.mybatis.dao;

import com.brickart.hotelmanagement.domain.Booking;

import java.util.List;

public interface BookingDao {

    void create(Booking booking);

    void update(Booking booking);

    void delete(Long id);

    Booking findById(Long id);

    List<Booking> findAll();

    List<Booking> findAllWithDetails();
}