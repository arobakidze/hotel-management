package com.brickart.hotelmanagement.persistence.mybatis.dao;

import com.brickart.hotelmanagement.domain.Department;

import java.util.List;

public interface DepartmentDao {

    void create(Department department);

    void update(Department department);

    void delete(Long id);

    List<Department> findByHotelId(Long hotelId);
}
