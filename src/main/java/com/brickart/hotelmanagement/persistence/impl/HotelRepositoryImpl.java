package com.brickart.hotelmanagement.persistence.impl;

import com.brickart.hotelmanagement.domain.Hotel;
import com.brickart.hotelmanagement.persistence.ConnectionPool;
import com.brickart.hotelmanagement.persistence.HotelRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelRepositoryImpl implements HotelRepository {

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

    private static final String CREATE = "INSERT INTO hotels (name, address, phone, star_rating) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE hotels SET name = ?, address = ?, phone = ?, star_rating = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM hotels WHERE id = ?";
    private static final String FIND_BY_ID = "SELECT id, name, address, phone, star_rating FROM hotels WHERE id = ?";
    private static final String FIND_ALL = "SELECT id, name, address, phone, star_rating FROM hotels";

    @Override
    public void create(Hotel hotel) {
        Connection connection = CONNECTION_POOL.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, hotel.getName());
            ps.setString(2, hotel.getAddress());
            ps.setString(3, hotel.getPhone());
            ps.setInt(4, hotel.getStarRating());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                hotel.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create hotel.", e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public void update(Hotel hotel) {
        Connection connection = CONNECTION_POOL.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setString(1, hotel.getName());
            ps.setString(2, hotel.getAddress());
            ps.setString(3, hotel.getPhone());
            ps.setInt(4, hotel.getStarRating());
            ps.setLong(5, hotel.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to update hotel.", e);
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
            throw new RuntimeException("Unable to delete hotel.", e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public Hotel findById(Long id) {
        Connection connection = CONNECTION_POOL.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapHotel(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find hotel.", e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public List<Hotel> findAll() {
        List<Hotel> hotels;
        Connection connection = CONNECTION_POOL.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL)) {
            ResultSet rs = ps.executeQuery();
            hotels = mapHotels(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find all hotels.", e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return hotels;
    }

    private Hotel mapHotel(ResultSet rs) throws SQLException {
        Hotel hotel = new Hotel(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("address"),
                rs.getString("phone"),
                rs.getInt("star_rating")
        );
        return hotel;
    }

    private List<Hotel> mapHotels(ResultSet rs) throws SQLException {
        List<Hotel> hotels = new ArrayList<>();
        while (rs.next()) {
            hotels.add(mapHotel(rs));
        }
        return hotels;
    }
}