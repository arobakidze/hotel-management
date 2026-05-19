package com.brickart.hotelmanagement.domain;

import java.time.LocalDate;

public class Guest {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String passportNumber;
    private LocalDate dateOfBirth;

    public Guest(Long id, String firstName, String lastName, String email,
                 String phone, String passportNumber, LocalDate dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.passportNumber = passportNumber;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
