package com.brickart.hotelmanagement.tests;

import com.brickart.hotelmanagement.domain.Booking;
import com.brickart.hotelmanagement.domain.Guest;
import com.brickart.hotelmanagement.domain.Room;
import com.brickart.hotelmanagement.service.BookingService;
import com.brickart.hotelmanagement.service.impl.BookingServiceImpl;
import com.brickart.hotelmanagement.tests.support.IntegrationTestBase;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingServiceTest extends IntegrationTestBase {

    private final BookingService bookingService = new BookingServiceImpl();
    private final List<Long> bookingIdsToCleanup = new ArrayList<>();

    private Long seededHotelId;
    private Long seededRoomTypeId;
    private Long seededRoomId;
    private Long seededGuestId;
    private Guest seededGuest;
    private Room seededRoom;

    @BeforeClass
    public void seedBookingDependencies() {
        seededHotelId = createHotel("Booking Seed Hotel").getId();
        seededRoomTypeId = insertRoomType("Booking Standard", 2, new BigDecimal("120.00"));
        seededRoom = createRoomForHotel(seededHotelId, seededRoomTypeId, 305, 3);
        seededRoomId = seededRoom.getId();
        seededGuest = createGuest("BookingGuest");
        seededGuestId = seededGuest.getId();
    }

    @AfterClass
    public void cleanupBookingData() {
        for (Long bookingId : bookingIdsToCleanup) {
            cleanupBooking(bookingId);
        }
        cleanupGuest(seededGuestId);
        cleanupHotel(seededHotelId);
        cleanupRoomType(seededRoomTypeId);
    }

  private Booking newBooking(LocalDate checkIn, LocalDate checkOut, boolean paid) {
    Guest guestRef = new Guest(seededGuestId, null, null, null, null, null, null);
    Room roomRef = new Room(seededRoomId, seededHotelId, seededRoom.getRoomNumber(), seededRoom.getFloor(), true, null);
    Booking booking = new Booking(null, checkIn, checkOut, LocalDateTime.now(), paid, guestRef, roomRef);
    return booking;
  }

    @Test
    public void createBookingThenFindByIdShouldReturnMatchingFields() {
        LocalDate checkIn = LocalDate.of(2025, 7, 1);
        LocalDate checkOut = LocalDate.of(2025, 7, 5);
        Booking created = bookingService.create(newBooking(checkIn, checkOut, false));
        bookingIdsToCleanup.add(created.getId());

        Booking found = bookingService.findById(created.getId());

        Assert.assertNotNull(found, "findById should return a booking after create()");
        Assert.assertEquals(found.getCheckIn(), checkIn,
                "Booking check-in from findById should match value passed to create()");
        Assert.assertEquals(found.getCheckOut(), checkOut,
                "Booking check-out from findById should match value passed to create()");
        Assert.assertFalse(found.isPaid(),
                "Booking paid flag from findById should match value passed to create()");
        Assert.assertEquals(found.getGuest().getId(), seededGuestId,
                "Booking guest id from findById should match seeded guest");
        Assert.assertEquals(found.getRoom().getId(), seededRoomId,
                "Booking room id from findById should match seeded room");
    }

    @Test
    public void updateBookingShouldPersistPaidFlagChange() {
        Booking created = bookingService.create(newBooking(
                LocalDate.of(2025, 8, 1),
                LocalDate.of(2025, 8, 3),
                false
        ));
        bookingIdsToCleanup.add(created.getId());

        Booking toUpdate = bookingService.findById(created.getId());
        toUpdate.setPaid(true);
        bookingService.update(toUpdate);

        Booking found = bookingService.findById(created.getId());
        Assert.assertTrue(found.isPaid(),
                "Booking paid flag should be true after update()");
    }

    @Test
    public void deleteBookingShouldRemoveRecordFromDatabase() {
        Booking created = bookingService.create(newBooking(
                LocalDate.of(2025, 9, 1),
                LocalDate.of(2025, 9, 2),
                false
        ));

        bookingService.delete(created.getId());

        Booking found = bookingService.findById(created.getId());
        Assert.assertNull(found, "findById should return null after delete()");
    }

    @Test
    public void findAllShouldIncludeCreatedBooking() {
        Booking created = bookingService.create(newBooking(
                LocalDate.of(2025, 10, 1),
                LocalDate.of(2025, 10, 4),
                false
        ));
        bookingIdsToCleanup.add(created.getId());

        List<Booking> bookings = bookingService.findAll();
        boolean containsCreated = bookings.stream()
                .anyMatch(b -> b.getId().equals(created.getId()));

        Assert.assertTrue(containsCreated,
                "findAll() should include the booking created in this test");
    }

    @Test
    public void findAllWithDetailsShouldReturnGuestAndRoomData() {
        Booking created = bookingService.create(newBooking(
                LocalDate.of(2025, 11, 1),
                LocalDate.of(2025, 11, 3),
                false
        ));
        bookingIdsToCleanup.add(created.getId());

        List<Booking> detailedBookings = bookingService.findAllWithDetails();
        Booking detailed = detailedBookings.stream()
                .filter(b -> b.getId().equals(created.getId()))
                .findFirst()
                .orElse(null);

        Assert.assertNotNull(detailed,
                "findAllWithDetails() should return the booking created in this test");
        Assert.assertEquals(detailed.getGuest().getFirstName(), seededGuest.getFirstName(),
                "findAllWithDetails() should load guest first name from database");
        Assert.assertEquals(detailed.getRoom().getRoomNumber(), seededRoom.getRoomNumber(),
                "findAllWithDetails() should load room number from database");
    }

    @Test
    public void createBookingShouldReturnBookingWithGeneratedId() {
        Booking created = bookingService.create(newBooking(
                LocalDate.of(2025, 12, 1),
                LocalDate.of(2025, 12, 2),
                true
        ));
        bookingIdsToCleanup.add(created.getId());

        Assert.assertNotNull(created.getId(),
                "create() should return booking with generated id");
        Assert.assertTrue(created.getId() > 0,
                "Generated booking id should be a positive number");
    }
}
