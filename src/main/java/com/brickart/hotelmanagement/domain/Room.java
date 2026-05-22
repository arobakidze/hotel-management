package com.brickart.hotelmanagement.domain;

import java.util.List;

public class Room {

    private Long id;
    private int roomNumber;
    private int floor;
    private boolean available;
    private RoomType roomType;
    private List<Amenity> amenities;
    private Long hotelId;

    public Room(Long id, Long hotelId, int roomNumber, int floor, boolean available, RoomType roomType) {
        this.id = id;
        this.hotelId = hotelId;
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.available = available;
        this.roomType = roomType;
    }

    public Room() {

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

}
