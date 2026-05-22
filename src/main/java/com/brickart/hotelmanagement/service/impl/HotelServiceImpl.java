package com.brickart.hotelmanagement.service.impl;

import com.brickart.hotelmanagement.domain.Department;
import com.brickart.hotelmanagement.domain.Hotel;
import com.brickart.hotelmanagement.domain.Room;
import com.brickart.hotelmanagement.persistence.mybatis.MyBatisUtil;
import com.brickart.hotelmanagement.persistence.mybatis.dao.HotelDao;
import com.brickart.hotelmanagement.service.DepartmentService;
import com.brickart.hotelmanagement.service.HotelService;
import com.brickart.hotelmanagement.service.RoomService;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class HotelServiceImpl implements HotelService {

    private final DepartmentService departmentService = new DepartmentServiceImpl();
    private final RoomService roomService = new RoomServiceImpl();

    @Override
    public void create(Hotel hotel) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            HotelDao hotelDao = session.getMapper(HotelDao.class);
            hotelDao.create(hotel);
            session.commit();
        }
    }

    @Override
    public void createHotelWithDetails(Hotel hotel) {
        create(hotel);

        if (hotel.getDepartments() != null) {
            for (Department department : hotel.getDepartments()) {
                department.setHotelId(hotel.getId());
                departmentService.create(department);
            }
        }

        if (hotel.getRooms() != null) {
            for (Room room : hotel.getRooms()) {
                room.setHotelId(hotel.getId());
                roomService.create(room);
            }
        }
    }

    @Override
    public void update(Hotel hotel) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            HotelDao hotelDao = session.getMapper(HotelDao.class);
            hotelDao.update(hotel);
            session.commit();
        }
    }

    @Override
    public void delete(Long id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            HotelDao hotelDao = session.getMapper(HotelDao.class);
            hotelDao.delete(id);
            session.commit();
        }
    }

    @Override
    public Hotel findById(Long id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            HotelDao hotelDao = session.getMapper(HotelDao.class);
            return hotelDao.findById(id);
        }
    }

    @Override
    public List<Hotel> findAll() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            HotelDao hotelDao = session.getMapper(HotelDao.class);
            return hotelDao.findAll();
        }
    }
}