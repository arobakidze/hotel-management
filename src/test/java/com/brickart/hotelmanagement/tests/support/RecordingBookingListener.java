package com.brickart.hotelmanagement.tests.support;

import com.brickart.hotelmanagement.domain.Booking;
import com.brickart.hotelmanagement.patterns.listener.BookingEventListener;

public class RecordingBookingListener implements BookingEventListener {

    private boolean bookingCreatedCalled = false;
    private boolean bookingCancelledCalled = false;
    private Booking createdBooking;
    private Long cancelledBookingId;

    @Override
    public void onBookingCreated(Booking booking) {
        bookingCreatedCalled = true;
        createdBooking = booking;
    }

    @Override
    public void onBookingCancelled(Long bookingId) {
        bookingCancelledCalled = true;
        cancelledBookingId = bookingId;
    }

    public boolean isBookingCreatedCalled() {
        return bookingCreatedCalled;
    }

    public boolean isBookingCancelledCalled() {
        return bookingCancelledCalled;
    }

    public Booking getCreatedBooking() {
        return createdBooking;
    }

    public Long getCancelledBookingId() {
        return cancelledBookingId;
    }
}
