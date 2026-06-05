package com.brickart.hotelmanagement.patterns.facade;

import com.brickart.hotelmanagement.domain.Booking;
import com.brickart.hotelmanagement.domain.Hotel;
import com.brickart.hotelmanagement.service.BookingService;
import com.brickart.hotelmanagement.service.HotelService;

import java.util.List;

public class HotelManagementFacade {

    private final HotelService hotelService;
    private final BookingService bookingService;

    public HotelManagementFacade(HotelService hotelService, BookingService bookingService) {
        this.hotelService = hotelService;
        this.bookingService = bookingService;
    }

    public void createHotelWithDetails(Hotel hotel) {
        hotelService.createHotelWithDetails(hotel);
    }

    public Booking createBooking(Booking booking) {
        return bookingService.create(booking);
    }

    public void cancelBooking(Long bookingId) {
        bookingService.delete(bookingId);
    }

    public List<Booking> getAllBookingsWithDetails() {
        return bookingService.findAllWithDetails();
    }

    public List<Hotel> getAllHotels() {
        return hotelService.findAll();
    }
}