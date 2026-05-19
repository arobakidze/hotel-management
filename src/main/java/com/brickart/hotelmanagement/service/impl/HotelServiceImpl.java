package com.brickart.hotelmanagement.service.impl;

import com.brickart.hotelmanagement.domain.Department;
import com.brickart.hotelmanagement.domain.Hotel;
import com.brickart.hotelmanagement.domain.Room;
import com.brickart.hotelmanagement.persistence.DepartmentRepository;
import com.brickart.hotelmanagement.persistence.HotelRepository;
import com.brickart.hotelmanagement.persistence.RoomRepository;
import com.brickart.hotelmanagement.persistence.impl.DepartmentRepositoryImpl;
import com.brickart.hotelmanagement.persistence.impl.HotelRepositoryImpl;
import com.brickart.hotelmanagement.persistence.impl.RoomRepositoryImpl;
import com.brickart.hotelmanagement.service.HotelService;

import java.util.List;

public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository = new HotelRepositoryImpl();
    private final DepartmentRepository departmentRepository = new DepartmentRepositoryImpl();
    private final RoomRepository roomRepository = new RoomRepositoryImpl();

    @Override
    public void create(Hotel hotel) {
        hotelRepository.create(hotel);
    }

    @Override
    public void createHotelWithDetails(Hotel hotel) {
        hotelRepository.create(hotel);

        if (hotel.getDepartments() != null) {
            for (Department department : hotel.getDepartments()) {
                department.setHotelId(hotel.getId());
                departmentRepository.create(department);
            }
        }

        if (hotel.getRooms() != null) {
            for (Room room : hotel.getRooms()) {
                room.setHotelId(hotel.getId());
                roomRepository.create(room);
            }
        }
    }

    @Override
    public void update(Hotel hotel) {
        hotelRepository.update(hotel);
    }

    @Override
    public void delete(Long id) {
        hotelRepository.delete(id);
    }

    @Override
    public Hotel findById(Long id) {
        return hotelRepository.findById(id);
    }

    @Override
    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }
}