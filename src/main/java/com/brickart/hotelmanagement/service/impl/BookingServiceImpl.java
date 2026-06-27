package com.brickart.hotelmanagement.service.impl;

import com.brickart.hotelmanagement.domain.Booking;
import com.brickart.hotelmanagement.persistence.mybatis.MyBatisUtil;
import com.brickart.hotelmanagement.persistence.mybatis.dao.BookingDao;
import com.brickart.hotelmanagement.service.BookingService;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class BookingServiceImpl implements BookingService {

    @Override
    public Booking create(Booking booking) {
        booking.setId(null);

        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BookingDao bookingDao = session.getMapper(BookingDao.class);
            bookingDao.create(booking);
            session.commit();
        }

        return booking;
    }

    @Override
    public void update(Booking booking) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BookingDao bookingDao = session.getMapper(BookingDao.class);
            bookingDao.update(booking);
            session.commit();
        }
    }

    @Override
    public void delete(Long id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BookingDao bookingDao = session.getMapper(BookingDao.class);
            bookingDao.delete(id);
            session.commit();
        }
    }

    @Override
    public Booking findById(Long id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BookingDao bookingDao = session.getMapper(BookingDao.class);
            return bookingDao.findById(id);
        }
    }

    @Override
    public List<Booking> findAll() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BookingDao bookingDao = session.getMapper(BookingDao.class);
            return bookingDao.findAll();
        }
    }

    @Override
    public List<Booking> findAllWithDetails() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            BookingDao bookingDao = session.getMapper(BookingDao.class);
            return bookingDao.findAllWithDetails();
        }
    }
}