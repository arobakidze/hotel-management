package com.brickart.hotelmanagement.persistence.impl;

import com.brickart.hotelmanagement.domain.Department;
import com.brickart.hotelmanagement.persistence.ConnectionPool;
import com.brickart.hotelmanagement.persistence.DepartmentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepositoryImpl implements DepartmentRepository {

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

    private static final String CREATE = "INSERT INTO departments (hotel_id, name, location) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE departments SET hotel_id = ?, name = ?, location = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM departments WHERE id = ?";
    private static final String FIND_BY_HOTEL_ID = "SELECT id, hotel_id, name, location FROM departments WHERE hotel_id = ?";

    @Override
    public void create(Department department) {
        Connection connection = CONNECTION_POOL.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, department.getHotelId());
            ps.setString(2, department.getName());
            ps.setString(3, department.getLocation());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                department.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create department.", e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public void update(Department department) {
        Connection connection = CONNECTION_POOL.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setLong(1, department.getHotelId());
            ps.setString(2, department.getName());
            ps.setString(3, department.getLocation());
            ps.setLong(4, department.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to update department.", e);
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
            throw new RuntimeException("Unable to delete department.", e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public List<Department> findByHotelId(Long hotelId) {
        List<Department> departments;
        Connection connection = CONNECTION_POOL.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_HOTEL_ID)) {
            ps.setLong(1, hotelId);
            ResultSet rs = ps.executeQuery();
            departments = mapDepartments(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find departments by hotel.", e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return departments;
    }

    private Department mapDepartment(ResultSet rs) throws SQLException {
        return new Department(
                rs.getLong("id"),
                rs.getLong("hotel_id"),
                rs.getString("name"),
                rs.getString("location")
        );
    }

    private List<Department> mapDepartments(ResultSet rs) throws SQLException {
        List<Department> departments = new ArrayList<>();
        while (rs.next()) {
            departments.add(mapDepartment(rs));
        }
        return departments;
    }
}