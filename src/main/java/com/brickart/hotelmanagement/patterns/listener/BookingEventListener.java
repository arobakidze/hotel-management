package com.brickart.hotelmanagement.patterns.listener;

import com.brickart.hotelmanagement.domain.Booking;

public interface BookingEventListener {

    void onBookingCreated(Booking booking);

    void onBookingCancelled(Long bookingId);
}