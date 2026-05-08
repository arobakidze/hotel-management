package com.brickart.hotelmanagement;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Staff {

    private Long id;
    private String firstName;
    private String lastName;
    private String position;
    private BigDecimal salary;
    private LocalDate hireDate;
    private boolean active;

    public Staff(Long id, String firstName, String lastName, String position,
                 BigDecimal salary, LocalDate hireDate, boolean active) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
        this.active = active;
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

    public String getPosition() {
        return position;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public boolean isActive() {
        return active;
    }
}
