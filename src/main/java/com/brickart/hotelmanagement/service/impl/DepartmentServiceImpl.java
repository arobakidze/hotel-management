package com.brickart.hotelmanagement.service.impl;

import com.brickart.hotelmanagement.domain.Department;
import com.brickart.hotelmanagement.persistence.mybatis.MyBatisUtil;
import com.brickart.hotelmanagement.persistence.mybatis.dao.DepartmentDao;
import com.brickart.hotelmanagement.service.DepartmentService;
import org.apache.ibatis.session.SqlSession;

public class DepartmentServiceImpl implements DepartmentService {

    @Override
    public void create(Department department) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            DepartmentDao departmentDao = session.getMapper(DepartmentDao.class);
            departmentDao.create(department);
            session.commit();
        }
    }
}