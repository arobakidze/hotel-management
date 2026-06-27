package com.brickart.hotelmanagement.tests.support;

import com.brickart.hotelmanagement.domain.Guest;
import com.brickart.hotelmanagement.domain.Hotel;
import com.brickart.hotelmanagement.domain.Room;
import com.brickart.hotelmanagement.domain.RoomType;
import com.brickart.hotelmanagement.persistence.GuestRepository;
import com.brickart.hotelmanagement.persistence.impl.GuestRepositoryImpl;
import com.brickart.hotelmanagement.service.HotelService;
import com.brickart.hotelmanagement.service.RoomService;
import com.brickart.hotelmanagement.service.impl.HotelServiceImpl;
import com.brickart.hotelmanagement.service.impl.RoomServiceImpl;
import org.testng.annotations.BeforeSuite;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.UUID;

public abstract class IntegrationTestBase {

    protected final HotelService hotelService = new HotelServiceImpl();
    protected final RoomService roomService = new RoomServiceImpl();
    protected final GuestRepository guestRepository = new GuestRepositoryImpl();

    static {
        DatabaseTestSupport.initializeSchema();
    }

    @BeforeSuite(alwaysRun = true)
    public void ensureSchemaInitialized() {
        DatabaseTestSupport.initializeSchema();
    }

    protected String uniqueSuffix() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    protected Hotel createHotel(String namePrefix) {
        String suffix = uniqueSuffix();
        Hotel hotel = new Hotel(
                null,
                namePrefix + " " + suffix,
                "Address " + suffix,
                "555-" + suffix,
                4
        );
        hotelService.create(hotel);
        return hotel;
    }

    protected Long insertRoomType(String name, int capacity, BigDecimal pricePerNight) {
        try (Connection connection = DatabaseTestSupport.openConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO room_types (name, capacity, price_per_night) VALUES (?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.setInt(2, capacity);
            ps.setBigDecimal(3, pricePerNight);
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                return keys.getLong(1);
            }
            throw new RuntimeException("Room type insert did not return generated key");
        } catch (Exception e) {
            throw new RuntimeException("Failed to insert room type", e);
        }
    }

    protected Room createRoomForHotel(Long hotelId, Long roomTypeId, int roomNumber, int floor) {
        RoomType roomType = new RoomType(roomTypeId, "Standard", 2, new BigDecimal("100.00"));
        Room room = new Room(null, hotelId, roomNumber, floor, true, roomType);
        roomService.create(room);
        return room;
    }

    protected Guest createGuest(String firstNamePrefix) {
        String suffix = uniqueSuffix();
        Guest guest = new Guest(
                null,
                firstNamePrefix,
                "Guest",
                firstNamePrefix.toLowerCase() + "." + suffix + "@test.com",
                "555-" + suffix,
                "PASS-" + suffix,
                LocalDate.of(1990, 1, 15)
        );
        guestRepository.create(guest);
        return guest;
    }

    protected void cleanupHotel(Long hotelId) {
        DatabaseTestSupport.executeUpdate(
                "DELETE FROM services WHERE booking_id IN (SELECT id FROM bookings WHERE room_id IN (SELECT id FROM rooms WHERE hotel_id = " + hotelId + "))");
        DatabaseTestSupport.executeUpdate(
                "DELETE FROM bookings WHERE room_id IN (SELECT id FROM rooms WHERE hotel_id = " + hotelId + ")");
        DatabaseTestSupport.executeUpdate("DELETE FROM room_amenities WHERE room_id IN (SELECT id FROM rooms WHERE hotel_id = " + hotelId + ")");
        DatabaseTestSupport.executeUpdate("DELETE FROM rooms WHERE hotel_id = " + hotelId);
        DatabaseTestSupport.executeUpdate("DELETE FROM staff WHERE department_id IN (SELECT id FROM departments WHERE hotel_id = " + hotelId + ")");
        DatabaseTestSupport.executeUpdate("DELETE FROM departments WHERE hotel_id = " + hotelId);
        DatabaseTestSupport.executeUpdate("DELETE FROM hotels WHERE id = " + hotelId);
    }

    protected void cleanupGuest(Long guestId) {
        DatabaseTestSupport.executeUpdate("DELETE FROM bookings WHERE guest_id = " + guestId);
        DatabaseTestSupport.executeUpdate("DELETE FROM guests WHERE id = " + guestId);
    }

    protected void cleanupRoomType(Long roomTypeId) {
        DatabaseTestSupport.executeUpdate("DELETE FROM room_types WHERE id = " + roomTypeId);
    }

    protected void cleanupBooking(Long bookingId) {
        DatabaseTestSupport.executeUpdate("DELETE FROM services WHERE booking_id = " + bookingId);
        DatabaseTestSupport.executeUpdate("DELETE FROM bookings WHERE id = " + bookingId);
    }
}
