package com.brickart.hotelmanagement.domain;

import java.math.BigDecimal;

public class RoomType {

    private Long id;
    private String name;
    private int capacity;
    private BigDecimal pricePerNight;

    public RoomType(Long id, String name, int capacity, BigDecimal pricePerNight) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
    }

    public RoomType() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }
}
