package com.brickart.hotelmanagement.persistence;

import com.brickart.hotelmanagement.domain.Guest;

import java.util.List;

public interface GuestRepository {
    void create(Guest guest);

    void update(Guest guest);

    void delete(Long id);

    Guest findById(Long id);

    List<Guest> findAll();
}