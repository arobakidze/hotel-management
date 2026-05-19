package com.brickart.hotelmanagement.persistence.impl;

import com.brickart.hotelmanagement.domain.Booking;
import com.brickart.hotelmanagement.domain.Guest;
import com.brickart.hotelmanagement.domain.Room;
import com.brickart.hotelmanagement.domain.RoomType;
import com.brickart.hotelmanagement.persistence.BookingRepository;
import com.brickart.hotelmanagement.persistence.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingRepositoryImpl implements BookingRepository {

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.getInstance();

    private static final String CREATE =
            "INSERT INTO bookings (guest_id, room_id, check_in, check_out, created_at, is_paid) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String UPDATE =
            "UPDATE bookings SET guest_id = ?, room_id = ?, check_in = ?, check_out = ?, is_paid = ? WHERE id = ?";

    private static final String DELETE =
            "DELETE FROM bookings WHERE id = ?";

    private static final String FIND_BY_ID =
            "SELECT id, guest_id, room_id, check_in, check_out, created_at, is_paid FROM bookings WHERE id = ?";

    private static final String FIND_ALL =
            "SELECT id, guest_id, room_id, check_in, check_out, created_at, is_paid FROM bookings";

    private static final String FIND_ALL_WITH_DETAILS =
            "SELECT b.id as booking_id, b.check_in, b.check_out, b.created_at, b.is_paid, " +
                    "g.id as guest_id, g.first_name as guest_first_name, g.last_name as guest_last_name, " +
                    "g.email, g.phone, g.passport_number, g.date_of_birth, " +
                    "r.id as room_id, r.room_number, r.floor, r.is_available, " +
                    "rt.id as room_type_id, rt.name as room_type_name, rt.capacity, rt.price_per_night, " +
                    "h.id as hotel_id, h.name as hotel_name, h.address, " +
                    "h.phone as hotel_phone, h.star_rating, " +
                    "s.id as service_id, s.name as service_name, s.price as service_price " +
                    "FROM bookings b " +
                    "JOIN guests g ON g.id = b.guest_id " +
                    "JOIN rooms r ON r.id = b.room_id " +
                    "JOIN room_types rt ON rt.id = r.room_type_id " +
                    "JOIN hotels h ON h.id = r.hotel_id " +
                    "LEFT JOIN services s ON s.booking_id = b.id";

    @Override
    public void create(Booking booking) {
        Connection connection = CONNECTION_POOL.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, booking.getGuest().getId());
            ps.setLong(2, booking.getRoom().getId());
            ps.setDate(3, Date.valueOf(booking.getCheckIn()));
            ps.setDate(4, Date.valueOf(booking.getCheckOut()));
            ps.setTimestamp(5, Timestamp.valueOf(booking.getCreatedAt()));
            ps.setBoolean(6, booking.isPaid());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            while (rs.next()) {
                booking.setId(rs.getLong(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Unable to create booking.", e);

        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public void update(Booking booking) {
        Connection connection = CONNECTION_POOL.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(UPDATE)) {

            ps.setLong(1, booking.getGuest().getId());
            ps.setLong(2, booking.getRoom().getId());
            ps.setDate(3, Date.valueOf(booking.getCheckIn()));
            ps.setDate(4, Date.valueOf(booking.getCheckOut()));
            ps.setBoolean(5, booking.isPaid());
            ps.setLong(6, booking.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Unable to update booking.", e);

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
            throw new RuntimeException("Unable to delete booking.", e);

        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }
    }

    @Override
    public Booking findById(Long id) {
        Connection connection = CONNECTION_POOL.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)) {

            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapBookingSimple(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Unable to find booking.", e);

        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }

        return null;
    }

    @Override
    public List<Booking> findAll() {
        List<Booking> bookings;

        Connection connection = CONNECTION_POOL.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL)) {

            ResultSet rs = ps.executeQuery();
            bookings = mapBookingsSimple(rs);

        } catch (SQLException e) {
            throw new RuntimeException("Unable to find all bookings.", e);

        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }

        return bookings;
    }

    @Override
    public List<Booking> findAllWithDetails() {
        List<Booking> bookings;

        Connection connection = CONNECTION_POOL.getConnection();

        try (PreparedStatement ps = connection.prepareStatement(FIND_ALL_WITH_DETAILS)) {

            ResultSet rs = ps.executeQuery();
            bookings = mapBookingsWithDetails(rs);

        } catch (SQLException e) {
            throw new RuntimeException("Unable to find bookings with details.", e);

        } finally {
            CONNECTION_POOL.releaseConnection(connection);
        }

        return bookings;
    }

    private Booking mapBookingSimple(ResultSet rs) throws SQLException {

        Guest guest = new Guest(
                rs.getLong("guest_id"),
                null,
                null,
                null,
                null,
                null,
                null
        );

        Room room = new Room(
                rs.getLong("room_id"),
                null,
                0,
                0,
                false,
                null
        );

        return new Booking(
                rs.getLong("id"),
                rs.getDate("check_in").toLocalDate(),
                rs.getDate("check_out").toLocalDate(),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getBoolean("is_paid"),
                guest,
                room
        );
    }

    private List<Booking> mapBookingsSimple(ResultSet rs) throws SQLException {

        List<Booking> bookings = new ArrayList<>();

        while (rs.next()) {
            bookings.add(mapBookingSimple(rs));
        }

        return bookings;
    }

    private List<Booking> mapBookingsWithDetails(ResultSet rs) throws SQLException {

        List<Booking> bookings = new ArrayList<>();

        while (rs.next()) {

            RoomType roomType = new RoomType(
                    rs.getLong("room_type_id"),
                    rs.getString("room_type_name"),
                    rs.getInt("capacity"),
                    rs.getBigDecimal("price_per_night")
            );

            Room room = new Room(
                    rs.getLong("room_id"),
                    rs.getLong("hotel_id"),
                    rs.getInt("room_number"),
                    rs.getInt("floor"),
                    rs.getBoolean("is_available"),
                    roomType
            );

            Guest guest = new Guest(
                    rs.getLong("guest_id"),
                    rs.getString("guest_first_name"),
                    rs.getString("guest_last_name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("passport_number"),
                    rs.getDate("date_of_birth").toLocalDate()
            );

            Booking booking = new Booking(
                    rs.getLong("booking_id"),
                    rs.getDate("check_in").toLocalDate(),
                    rs.getDate("check_out").toLocalDate(),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getBoolean("is_paid"),
                    guest,
                    room
            );

            bookings.add(booking);
        }

        return bookings;
    }
}