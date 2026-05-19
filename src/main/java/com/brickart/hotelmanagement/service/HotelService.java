package com.brickart.hotelmanagement.service;

import com.brickart.hotelmanagement.domain.Hotel;

import java.util.List;

public interface HotelService {

    void create(Hotel hotel);

    void createHotelWithDetails(Hotel hotel);

    void update(Hotel hotel);

    void delete(Long id);

    Hotel findById(Long id);

    List<Hotel> findAll();
}