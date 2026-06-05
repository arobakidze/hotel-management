package com.brickart.hotelmanagement.patterns.listener;

import com.brickart.hotelmanagement.domain.Booking;

import java.util.ArrayList;
import java.util.List;

public class BookingEventManager {

    private final List<BookingEventListener> listeners = new ArrayList<>();

    public void addListener(BookingEventListener listener) {
        listeners.add(listener);
    }

    public void notifyBookingCreated(Booking booking) {
        for (BookingEventListener listener : listeners) {
            listener.onBookingCreated(booking);
        }
    }

    public void notifyBookingCancelled(Long bookingId) {
        for (BookingEventListener listener : listeners) {
            listener.onBookingCancelled(bookingId);
        }
    }
}