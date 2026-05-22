package com.brickart.hotelmanagement.persistence.mybatis.dao;

import com.brickart.hotelmanagement.domain.Hotel;

import java.util.List;

public interface HotelDao {

    void create(Hotel hotel);

    void update(Hotel hotel);

    void delete(Long id);

    Hotel findById(Long id);

    List<Hotel> findAll();
}