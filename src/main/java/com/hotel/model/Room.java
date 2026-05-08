package com.hotel.model;

import java.util.List;

public class Room {

    private Long id;
    private int roomNumber;
    private int floor;
    private boolean isAvailable;
    private RoomType roomType;
    private List<Amenity> amenities;

    public Room(Long id, int roomNumber, int floor, boolean isAvailable, RoomType roomType) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.isAvailable = isAvailable;
        this.roomType = roomType;
    }

    public Long getId() {
        return id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getFloor() {
        return floor;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }
}
