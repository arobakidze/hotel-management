package com.brickart.hotelmanagement.persistence.impl;

import com.brickart.hotelmanagement.domain.Room;
import com.brickart.hotelmanagement.domain.RoomType;
import com.brickart.hotelmanagement.persistence.ConnectionPool;
import com.brickart.hotelmanagement.persistence.RoomRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRepositoryImpl implements RoomRepository {

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

    private static final String CREATE = "INSERT INTO rooms (hotel_id, room_type_id, room_number, floor, is_available) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE rooms SET hotel_id = ?, room_type_id = ?, room_number = ?, floor = ?, is_available = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM rooms WHERE id = ?";
    private static final String FIND_BY_ID = "SELECT r.id, r.room_number, r.floor, r.is_available, rt.id as room_type_id, rt.name as room_type_name, rt.capacity, rt.price_per_night FROM rooms r JOIN room_types rt ON rt.id = r.room_type_id WHERE r.id = ?";
    private static final String FIND_BY_HOTEL_ID = "SELECT r.id, r.room_number, r.floor, r.is_available, rt.id as room_type_id, rt.name as room_type_name, rt.capacity, rt.price_per_night FROM rooms r JOIN room_types rt ON rt.id = r.room_type_id WHERE r.hotel_id = ?";

    @Override
    public void create(Room room) {
        Connection connection = CONNECTION_POOL.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, room.getHotelId());
            ps.setLong(2, room.getRoomType().getId());
            ps.setInt(3, room.getRoomNumber());
            ps.setInt(4, room.getFloor());
            ps.setBoolean(5, room.isAvailable());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                room.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create room.", e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public void update(Room room) {
        Connection connection = CONNECTION_POOL.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setLong(1, room.getHotelId());
            ps.setLong(2, room.getRoomType().getId());
            ps.setInt(3, room.getRoomNumber());
            ps.setInt(4, room.getFloor());
            ps.setBoolean(5, room.isAvailable());
            ps.setLong(6, room.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to update room.", e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public void delete(Long id) {
        Connection connection = CONNECTION_POOL.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(DELETE)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to delete room.", e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public Room findById(Long id) {
        Connection connection = CONNECTION_POOL.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRoom(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find room.", e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public List<Room> findByHotelId(Long hotelId) {
        List<Room> rooms;
        Connection connection = CONNECTION_POOL.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_HOTEL_ID)) {
            ps.setLong(1, hotelId);
            ResultSet rs = ps.executeQuery();
            rooms = mapRooms(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find rooms by hotel.", e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return rooms;
    }

    private Room mapRoom(ResultSet rs) throws SQLException {
        RoomType roomType = new RoomType(
                rs.getLong("room_type_id"),
                rs.getString("room_type_name"),
                rs.getInt("capacity"),
                rs.getBigDecimal("price_per_night")
        );
        return new Room(
                rs.getLong("id"),
                rs.getLong("hotel_id"),
                rs.getInt("room_number"),
                rs.getInt("floor"),
                rs.getBoolean("is_available"),
                roomType
        );
    }

    private List<Room> mapRooms(ResultSet rs) throws SQLException {
        List<Room> rooms = new ArrayList<>();
        while (rs.next()) {
            rooms.add(mapRoom(rs));
        }
        return rooms;
    }
}