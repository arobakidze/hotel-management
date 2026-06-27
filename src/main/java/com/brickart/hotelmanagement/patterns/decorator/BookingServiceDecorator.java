package com.brickart.hotelmanagement.patterns.decorator;

import com.brickart.hotelmanagement.domain.Booking;
import com.brickart.hotelmanagement.service.BookingService;

import java.util.List;

public abstract class BookingServiceDecorator implements BookingService {

    protected final BookingService bookingService;

    public BookingServiceDecorator(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Override
    public Booking create(Booking booking) {
        return bookingService.create(booking);
    }

    @Override
    public void update(Booking booking) {
        bookingService.update(booking);
    }

    @Override
    public void delete(Long id) {
        bookingService.delete(id);
    }

    @Override
    public Booking findById(Long id) {
        return bookingService.findById(id);
    }

    @Override
    public List<Booking> findAll() {
        return bookingService.findAll();
    }

    @Override
    public List<Booking> findAllWithDetails() {
        return bookingService.findAllWithDetails();
    }
}