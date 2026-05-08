package com.brickart.hotelmanagement;

import java.util.List;

public class Room {

    private Long id;
    private int roomNumber;
    private int floor;
    private boolean available;
    private RoomType roomType;
    private List<Amenity> amenities;

    public Room(Long id, int roomNumber, int floor, boolean available, RoomType roomType) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.available = available;
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
        return available;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }
}
