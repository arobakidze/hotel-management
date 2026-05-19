package com.brickart.hotelmanagement;

import com.brickart.hotelmanagement.domain.Booking;
import com.brickart.hotelmanagement.domain.Hotel;
import com.brickart.hotelmanagement.service.HotelService;
import com.brickart.hotelmanagement.service.BookingService;
import com.brickart.hotelmanagement.service.impl.BookingServiceImpl;
import com.brickart.hotelmanagement.service.impl.HotelServiceImpl;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        HotelService hotelService = new HotelServiceImpl();
        BookingService bookingService = new BookingServiceImpl();

        List<Hotel> hotels = hotelService.findAll();
        hotels.forEach(h -> System.out.println(h.getName() + " - " + h.getStarRating() + " stars"));

        List<Booking> bookings = bookingService.findAllWithDetails();
        bookings.forEach(b -> System.out.println(
                b.getGuest().getFirstName() + " " + b.getGuest().getLastName() +
                        " -> Room " + b.getRoom().getRoomNumber() +
                        " | " + b.getCheckIn() + " to " + b.getCheckOut()
        ));
    }
}