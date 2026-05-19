package com.brickart.hotelmanagement.persistence.impl;

import com.brickart.hotelmanagement.domain.Staff;
import com.brickart.hotelmanagement.persistence.ConnectionPool;
import com.brickart.hotelmanagement.persistence.StaffRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffRepositoryImpl implements StaffRepository {

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

    private static final String CREATE = "INSERT INTO staff (department_id, first_name, last_name, position, salary, hire_date, is_active) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE staff SET department_id = ?, first_name = ?, last_name = ?, position = ?, salary = ?, hire_date = ?, is_active = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM staff WHERE id = ?";
    private static final String FIND_BY_DEPARTMENT_ID = "SELECT id, department_id, first_name, last_name, position, salary, hire_date, is_active FROM staff WHERE department_id = ?";

    @Override
    public void create(Staff staff) {
        Connection connection = CONNECTION_POOL.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, staff.getDepartmentId());
            ps.setString(2, staff.getFirstName());
            ps.setString(3, staff.getLastName());
            ps.setString(4, staff.getPosition());
            ps.setBigDecimal(5, staff.getSalary());
            ps.setDate(6, Date.valueOf(staff.getHireDate()));
            ps.setBoolean(7, staff.isActive());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                staff.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to create staff.", e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public void update(Staff staff) {
        Connection connection = CONNECTION_POOL.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {
            ps.setLong(1, staff.getDepartmentId());
            ps.setString(2, staff.getFirstName());
            ps.setString(3, staff.getLastName());
            ps.setString(4, staff.getPosition());
            ps.setBigDecimal(5, staff.getSalary());
            ps.setDate(6, Date.valueOf(staff.getHireDate()));
            ps.setBoolean(7, staff.isActive());
            ps.setLong(8, staff.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to update staff.", e);
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
            throw new RuntimeException("Unable to delete staff.", e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public List<Staff> findByDepartmentId(Long departmentId) {
        List<Staff> staffList;
        Connection connection = CONNECTION_POOL.getConnection();
        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_DEPARTMENT_ID)) {
            ps.setLong(1, departmentId);
            ResultSet rs = ps.executeQuery();
            staffList = mapStaffList(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to find staff by department.", e);
        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
        return staffList;
    }

    private Staff mapStaff(ResultSet rs) throws SQLException {
        return new Staff(
                rs.getLong("id"),
                rs.getLong("department_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("position"),
                rs.getBigDecimal("salary"),
                rs.getDate("hire_date").toLocalDate(),
                rs.getBoolean("is_active")
        );
    }

    private List<Staff> mapStaffList(ResultSet rs) throws SQLException {
        List<Staff> staffList = new ArrayList<>();
        while (rs.next()) {
            staffList.add(mapStaff(rs));
        }
        return staffList;
    }
}