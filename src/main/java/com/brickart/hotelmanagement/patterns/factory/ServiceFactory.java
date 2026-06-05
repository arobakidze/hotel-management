package com.brickart.hotelmanagement.patterns.factory;

import com.brickart.hotelmanagement.service.BookingService;
import com.brickart.hotelmanagement.service.DepartmentService;
import com.brickart.hotelmanagement.service.HotelService;
import com.brickart.hotelmanagement.service.RoomService;
import com.brickart.hotelmanagement.service.impl.BookingServiceImpl;
import com.brickart.hotelmanagement.service.impl.DepartmentServiceImpl;
import com.brickart.hotelmanagement.service.impl.HotelServiceImpl;
import com.brickart.hotelmanagement.service.impl.RoomServiceImpl;

public class ServiceFactory {

    public HotelService createHotelService() {
        return new HotelServiceImpl();
    }

    public BookingService createBookingService() {
        return new BookingServiceImpl();
    }

    public DepartmentService createDepartmentService() {
        return new DepartmentServiceImpl();
    }

    public RoomService createRoomService() {
        return new RoomServiceImpl();
    }
}