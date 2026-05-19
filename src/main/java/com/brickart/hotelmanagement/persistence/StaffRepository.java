package com.brickart.hotelmanagement.persistence;

import com.brickart.hotelmanagement.domain.Staff;

import java.util.List;

public interface StaffRepository {
    void create(Staff staff);

    void update(Staff staff);

    void delete(Long id);

    List<Staff> findByDepartmentId(Long departmentId);
}