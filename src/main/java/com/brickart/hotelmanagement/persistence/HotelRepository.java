package com.brickart.hotelmanagement.persistence;

import com.brickart.hotelmanagement.domain.Hotel;

import java.util.List;

public interface HotelRepository {
    void create(Hotel hotel);

    void update(Hotel hotel);

    void delete(Long id);

    Hotel findById(Long id);

    List<Hotel> findAll();
}