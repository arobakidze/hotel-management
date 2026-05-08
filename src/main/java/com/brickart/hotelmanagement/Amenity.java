package com.brickart.hotelmanagement;

public class Amenity {

    private Long id;
    private String name;
    private boolean isComplimentary;

    public Amenity(Long id, String name, boolean isComplimentary) {
        this.id = id;
        this.name = name;
        this.isComplimentary = isComplimentary;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isComplimentary() {
        return isComplimentary;
    }
}
