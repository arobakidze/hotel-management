package com.brickart.hotelmanagement.service.impl;

import com.brickart.hotelmanagement.domain.Room;
import com.brickart.hotelmanagement.persistence.RoomRepository;
import com.brickart.hotelmanagement.persistence.impl.RoomRepositoryImpl;
import com.brickart.hotelmanagement.service.RoomService;

public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository = new RoomRepositoryImpl();

    @Override
    public void create(Room room) {
        roomRepository.create(room);
    }
}