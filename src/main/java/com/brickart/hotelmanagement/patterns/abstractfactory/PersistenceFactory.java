package com.brickart.hotelmanagement.patterns.abstractfactory;

import com.brickart.hotelmanagement.service.BookingService;
import com.brickart.hotelmanagement.service.HotelService;

public interface PersistenceFactory {

    HotelService createHotelService();

    BookingService createBookingService();
}