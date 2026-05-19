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
}
