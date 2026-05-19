package com.brickart.hotelmanagement.service.impl;

import com.brickart.hotelmanagement.domain.Booking;
import com.brickart.hotelmanagement.persistence.BookingRepository;
import com.brickart.hotelmanagement.persistence.impl.BookingRepositoryImpl;
import com.brickart.hotelmanagement.service.BookingService;

import java.util.List;

public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    public BookingServiceImpl() {
        this.bookingRepository = new BookingRepositoryImpl();
    }

    @Override
    public Booking create(Booking booking) {
        booking.setId(null);
        bookingRepository.create(booking);
        return booking;
    }

    @Override
    public void update(Booking booking) {
        bookingRepository.update(booking);
    }

    @Override
    public void delete(Long id) {
        bookingRepository.delete(id);
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> findAllWithDetails() {
        return bookingRepository.findAllWithDetails();
    }
}