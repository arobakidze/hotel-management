package com.brickart.hotelmanagement.service.impl;

import com.brickart.hotelmanagement.domain.Department;
import com.brickart.hotelmanagement.persistence.DepartmentRepository;
import com.brickart.hotelmanagement.persistence.impl.DepartmentRepositoryImpl;
import com.brickart.hotelmanagement.service.DepartmentService;

public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository = new DepartmentRepositoryImpl();

    @Override
    public void create(Department department) {
        departmentRepository.create(department);
    }
}