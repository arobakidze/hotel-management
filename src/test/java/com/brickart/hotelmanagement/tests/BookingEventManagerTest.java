package com.brickart.hotelmanagement.tests;

import com.brickart.hotelmanagement.domain.Booking;
import com.brickart.hotelmanagement.domain.Guest;
import com.brickart.hotelmanagement.domain.Room;
import com.brickart.hotelmanagement.domain.RoomType;
import com.brickart.hotelmanagement.patterns.listener.BookingEventManager;
import com.brickart.hotelmanagement.tests.support.RecordingBookingListener;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingEventManagerTest {

    private Booking sampleBooking(Long bookingId) {
        RoomType roomType = new RoomType(1L, "Deluxe", 2, new BigDecimal("200.00"));
        Room room = new Room(10L, 1L, 501, 5, true, roomType);
        Guest guest = new Guest(
                5L,
                "Alice",
                "Smith",
                "alice@test.com",
                "555-0100",
                "PASS-ALICE",
                LocalDate.of(1988, 3, 10)
        );
        return new Booking(
                bookingId,
                LocalDate.of(2025, 6, 10),
                LocalDate.of(2025, 6, 12),
                LocalDateTime.now(),
                false,
                guest,
                room
        );
    }

    @Test
    public void notifyBookingCreatedShouldCallRegisteredListener() {
        BookingEventManager manager = new BookingEventManager();
        RecordingBookingListener listener = new RecordingBookingListener();
        manager.addListener(listener);

        manager.notifyBookingCreated(sampleBooking(42L));

        Assert.assertTrue(listener.isBookingCreatedCalled(),
                "notifyBookingCreated() should invoke onBookingCreated on registered listener");
    }

    @Test
    public void notifyBookingCancelledShouldCallRegisteredListenerWithId() {
        BookingEventManager manager = new BookingEventManager();
        RecordingBookingListener listener = new RecordingBookingListener();
        manager.addListener(listener);

        manager.notifyBookingCancelled(99L);

        Assert.assertTrue(listener.isBookingCancelledCalled(),
                "notifyBookingCancelled() should invoke onBookingCancelled on registered listener");
        Assert.assertEquals(listener.getCancelledBookingId(), 99L,
                "Cancelled booking id passed to listener should match notifyBookingCancelled() argument");
    }

    @Test
    public void notifyBookingCreatedShouldPassBookingDataToListener() {
        BookingEventManager manager = new BookingEventManager();
        RecordingBookingListener listener = new RecordingBookingListener();
        manager.addListener(listener);

        Booking booking = sampleBooking(77L);
        manager.notifyBookingCreated(booking);

        Assert.assertEquals(listener.getCreatedBooking().getId(), 77L,
                "Listener should receive booking with the same id that was notified");
        Assert.assertEquals(listener.getCreatedBooking().getGuest().getFirstName(), "Alice",
                "Listener should receive booking with guest first name from notified booking");
    }

    @Test
    public void multipleListenersShouldAllReceiveBookingCreatedEvent() {
        BookingEventManager manager = new BookingEventManager();
        RecordingBookingListener firstListener = new RecordingBookingListener();
        RecordingBookingListener secondListener = new RecordingBookingListener();
        manager.addListener(firstListener);
        manager.addListener(secondListener);

        manager.notifyBookingCreated(sampleBooking(12L));

        Assert.assertTrue(firstListener.isBookingCreatedCalled(),
                "First listener should be notified when booking is created");
        Assert.assertTrue(secondListener.isBookingCreatedCalled(),
                "Second listener should be notified when booking is created");
    }

    @Test
    public void multipleListenersShouldAllReceiveBookingCancelledEvent() {
        BookingEventManager manager = new BookingEventManager();
        RecordingBookingListener firstListener = new RecordingBookingListener();
        RecordingBookingListener secondListener = new RecordingBookingListener();
        manager.addListener(firstListener);
        manager.addListener(secondListener);

        manager.notifyBookingCancelled(55L);

        Assert.assertTrue(firstListener.isBookingCancelledCalled(),
                "First listener should be notified when booking is cancelled");
        Assert.assertTrue(secondListener.isBookingCancelledCalled(),
                "Second listener should be notified when booking is cancelled");
        Assert.assertEquals(firstListener.getCancelledBookingId(), 55L,
                "First listener should receive cancelled booking id 55");
        Assert.assertEquals(secondListener.getCancelledBookingId(), 55L,
                "Second listener should receive cancelled booking id 55");
    }
}
