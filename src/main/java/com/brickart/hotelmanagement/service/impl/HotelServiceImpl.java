package com.brickart.hotelmanagement.service.impl;

import com.brickart.hotelmanagement.domain.Hotel;
import com.brickart.hotelmanagement.persistence.HotelRepository;
import com.brickart.hotelmanagement.persistence.impl.HotelRepositoryImpl;
import com.brickart.hotelmanagement.service.HotelService;

import java.util.List;

public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    public HotelServiceImpl() {
        this.hotelRepository = new HotelRepositoryImpl();
    }

    @Override
    public Hotel create(Hotel hotel) {
        hotel.setId(null);
        hotelRepository.create(hotel);
        return hotel;
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