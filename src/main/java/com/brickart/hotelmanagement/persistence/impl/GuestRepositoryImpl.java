package com.brickart.hotelmanagement.persistence.impl;

import com.brickart.hotelmanagement.domain.Guest;
import com.brickart.hotelmanagement.persistence.ConnectionPool;
import com.brickart.hotelmanagement.persistence.GuestRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuestRepositoryImpl implements GuestRepository {

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

    private static final String CREATE = "INSERT INTO guests (first_name, last_name, email, phone, passport_number, date_of_birth) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE guests SET first_name = ?, last_name = ?, email = ?, phone = ?, passport_number = ?, date_of_birth = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM guests WHERE id = ?";
    private static final String FIND_BY_ID = "SELECT id, first_name, last_name, email, phone, passport_number, date_of_birth FROM guests WHERE id = ?";
    private static final String FIND_ALL = "SELECT id, first_name, last_name, email, phone, passport_number, date_of_birth FROM guests";

    @Override
    public void create(Guest guest) {
        Connection connection = CONNECTION_POOL.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, guest.getFirstName());
            ps.setString(2, guest.getLastName());
            ps.setString(3, guest.getEmail());
            ps.setString(4, guest.getPhone());
            ps.setString(5, guest.getPassportNumber());
            ps.setDate(6, Date.valueOf(guest.getDateOfBirth()));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                guest.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create guest.", e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public void update(Guest guest) {
        Connection connection = CONNECTION_POOL.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setString(1, guest.getFirstName());
            ps.setString(2, guest.getLastName());
            ps.setString(3, guest.getEmail());
            ps.setString(4, guest.getPhone());
            ps.setString(5, guest.getPassportNumber());
            ps.setDate(6, Date.valueOf(guest.getDateOfBirth()));
            ps.setLong(7, guest.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to update guest.", e);
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
            throw new RuntimeException("Unable to delete guest.", e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public Guest findById(Long id) {
        Connection connection = CONNECTION_POOL.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapGuest(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find guest.", e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return null;
    }

    @Override
    public List<Guest> findAll() {
        List<Guest> guests;
        Connection connection = CONNECTION_POOL.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL)) {
            ResultSet rs = ps.executeQuery();
            guests = mapGuests(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find all guests.", e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return guests;
    }

    private Guest mapGuest(ResultSet rs) throws SQLException {
        return new Guest(
                rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("passport_number"),
                rs.getDate("date_of_birth").toLocalDate()
        );
    }

    private List<Guest> mapGuests(ResultSet rs) throws SQLException {
        List<Guest> guests = new ArrayList<>();
        while (rs.next()) {
            guests.add(mapGuest(rs));
        }
        return guests;
    }
}