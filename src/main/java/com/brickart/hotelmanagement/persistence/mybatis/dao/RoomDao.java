package com.brickart.hotelmanagement.persistence.mybatis.dao;

import com.brickart.hotelmanagement.domain.Room;

import java.util.List;

public interface RoomDao {

    void create(Room room);

    void update(Room room);

    void delete(Long id);

    Room findById(Long id);

    List<Room> findByHotelId(Long hotelId);
}