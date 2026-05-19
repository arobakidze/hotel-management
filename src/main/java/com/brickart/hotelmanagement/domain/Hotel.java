package com.brickart.hotelmanagement.domain;

import java.util.List;

public class Hotel {

    private Long id;
    private String name;
    private String address;
    private String phone;
    private int starRating;
    private List<Department> departments;
    private List<Room> rooms;

    public Hotel(Long id, String name, String address, String phone, int starRating) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.starRating = starRating;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public int getStarRating() {
        return starRating;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}