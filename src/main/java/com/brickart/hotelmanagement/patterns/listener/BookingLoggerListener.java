package com.brickart.hotelmanagement.patterns.listener;

import com.brickart.hotelmanagement.domain.Booking;

public class BookingLoggerListener implements BookingEventListener {

    @Override
    public void onBookingCreated(Booking booking) {
        System.out.println("Listener: booking created with id " + booking.getId());
    }

    @Override
    public void onBookingCancelled(Long bookingId) {
        System.out.println("Listener: booking cancelled with id " + bookingId);
    }
}