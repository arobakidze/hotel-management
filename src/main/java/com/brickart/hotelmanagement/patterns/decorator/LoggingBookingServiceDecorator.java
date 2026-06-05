package com.brickart.hotelmanagement.patterns.decorator;

import com.brickart.hotelmanagement.domain.Booking;
import com.brickart.hotelmanagement.service.BookingService;

public class LoggingBookingServiceDecorator extends BookingServiceDecorator {

    public LoggingBookingServiceDecorator(BookingService bookingService) {
        super(bookingService);
    }

    @Override
    public Booking create(Booking booking) {
        System.out.println("Decorator: creating booking...");
        Booking createdBooking = super.create(booking);
        System.out.println("Decorator: booking created with id " + createdBooking.getId());
        return createdBooking;
    }

    @Override
    public void delete(Long id) {
        System.out.println("Decorator: deleting booking with id " + id);
        super.delete(id);
        System.out.println("Decorator: booking deleted.");
    }
}