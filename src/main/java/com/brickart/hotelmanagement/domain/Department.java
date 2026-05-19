package com.brickart.hotelmanagement.domain;

import java.util.List;

public class Department {

    private Long id;
    private Long hotelId;
    private String name;
    private String location;
    private List<Staff> staff;

    public Department(Long id, Long hotelId, String name, String location) {
        this.id = id;
        this.hotelId = hotelId;
        this.name = name;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public List<Staff> getStaff() {
        return staff;
    }

    public void setStaff(List<Staff> staff) {
        this.staff = staff;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }
}