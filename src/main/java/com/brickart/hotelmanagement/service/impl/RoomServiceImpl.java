package com.brickart.hotelmanagement.service.impl;

import com.brickart.hotelmanagement.domain.Room;
import com.brickart.hotelmanagement.persistence.mybatis.MyBatisUtil;
import com.brickart.hotelmanagement.persistence.mybatis.dao.RoomDao;
import com.brickart.hotelmanagement.service.RoomService;
import org.apache.ibatis.session.SqlSession;

public class RoomServiceImpl implements RoomService {

    @Override
    public void create(Room room) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            RoomDao roomDao = session.getMapper(RoomDao.class);
            roomDao.create(room);
            session.commit();
        }
    }
}