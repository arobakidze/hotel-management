package com.brickart.hotelmanagement.domain;

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
    private Long departmentId;

    public Staff(Long id, Long departmentId, String firstName, String lastName,
                 String position, BigDecimal salary,
                 LocalDate hireDate, boolean active) {

        this.id = id;
        this.departmentId = departmentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
        this.active = active;
    }

    public Long getDepartmentId() {
        return departmentId;
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
    public void setId(Long id) {
        this.id = id;
    }
}
