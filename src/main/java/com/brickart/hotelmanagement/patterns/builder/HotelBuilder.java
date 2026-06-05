package com.brickart.hotelmanagement.patterns.builder;

import com.brickart.hotelmanagement.domain.Department;
import com.brickart.hotelmanagement.domain.Hotel;
import com.brickart.hotelmanagement.domain.Room;

import java.util.ArrayList;
import java.util.List;

public class HotelBuilder {

    private Long id;
    private String name;
    private String address;
    private String phone;
    private int starRating;
    private final List<Department> departments = new ArrayList<>();
    private final List<Room> rooms = new ArrayList<>();

    public HotelBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public HotelBuilder name(String name) {
        this.name = name;
        return this;
    }

    public HotelBuilder address(String address) {
        this.address = address;
        return this;
    }

    public HotelBuilder phone(String phone) {
        this.phone = phone;
        return this;
    }

    public HotelBuilder starRating(int starRating) {
        this.starRating = starRating;
        return this;
    }

    public HotelBuilder addDepartment(Department department) {
        this.departments.add(department);
        return this;
    }

    public HotelBuilder addRoom(Room room) {
        this.rooms.add(room);
        return this;
    }

    public Hotel build() {
        Hotel hotel = new Hotel(id, name, address, phone, starRating);
        hotel.setDepartments(departments);
        hotel.setRooms(rooms);
        return hotel;
    }
}