package com.brickart.hotelmanagement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Booking {

    private Long id;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private LocalDateTime createdAt;
    private boolean paid;
    private Guest guest;
    private Room room;
    private List<Service> services;

    public Booking(Long id, LocalDate checkIn, LocalDate checkOut,
                   LocalDateTime createdAt, boolean paid, Guest guest, Room room) {
        this.id = id;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.createdAt = createdAt;
        this.paid = paid;
        this.guest = guest;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isPaid() {
        return paid;
    }

    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
