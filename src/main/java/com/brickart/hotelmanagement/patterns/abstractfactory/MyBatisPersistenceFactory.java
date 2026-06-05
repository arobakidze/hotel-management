package com.brickart.hotelmanagement.patterns.abstractfactory;

import com.brickart.hotelmanagement.service.BookingService;
import com.brickart.hotelmanagement.service.HotelService;
import com.brickart.hotelmanagement.service.impl.BookingServiceImpl;
import com.brickart.hotelmanagement.service.impl.HotelServiceImpl;

public class MyBatisPersistenceFactory implements PersistenceFactory {

    @Override
    public HotelService createHotelService() {
        return new HotelServiceImpl();
    }

    @Override
    public BookingService createBookingService() {
        return new BookingServiceImpl();
    }
}