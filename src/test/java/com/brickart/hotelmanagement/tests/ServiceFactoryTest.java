package com.brickart.hotelmanagement.tests;

import com.brickart.hotelmanagement.domain.Booking;
import com.brickart.hotelmanagement.domain.Guest;
import com.brickart.hotelmanagement.domain.Hotel;
import com.brickart.hotelmanagement.domain.Room;
import com.brickart.hotelmanagement.patterns.factory.ServiceFactory;
import com.brickart.hotelmanagement.service.BookingService;
import com.brickart.hotelmanagement.service.HotelService;
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

public class ServiceFactoryTest extends IntegrationTestBase {

    private final ServiceFactory factory = new ServiceFactory();
    private final List<Long> hotelIdsToCleanup = new ArrayList<>();
    private final List<Long> bookingIdsToCleanup = new ArrayList<>();

    private Long seededRoomTypeId;
    private Long seededHotelId;
    private Long seededRoomId;
    private Long seededGuestId;

    @BeforeClass
    public void seedFactoryTestData() {
        seededHotelId = createHotel("Factory Seed Hotel").getId();
        seededRoomTypeId = insertRoomType("Factory Room Type", 2, new BigDecimal("95.00"));
        Room room = createRoomForHotel(seededHotelId, seededRoomTypeId, 110, 1);
        seededRoomId = room.getId();
        seededGuestId = createGuest("FactoryGuest").getId();
    }

    @AfterClass
    public void cleanupFactoryTestData() {
        for (Long bookingId : bookingIdsToCleanup) {
            cleanupBooking(bookingId);
        }
        cleanupGuest(seededGuestId);
        cleanupHotel(seededHotelId);
        cleanupRoomType(seededRoomTypeId);
    }

    @Test
    public void factoryShouldCreateWorkingHotelService() {
        HotelService hotelServiceFromFactory = factory.createHotelService();
        Hotel hotel = createHotel("Factory Hotel");
        hotelIdsToCleanup.add(hotel.getId());

        Hotel found = hotelServiceFromFactory.findById(hotel.getId());

        Assert.assertNotNull(found,
                "HotelService from factory should read a hotel persisted via create()");
        Assert.assertEquals(found.getName(), hotel.getName(),
                "Hotel name from factory-created service should match created hotel");
    }

    @Test
    public void factoryShouldCreateWorkingBookingService() {
        BookingService bookingServiceFromFactory = factory.createBookingService();
        Guest guestRef = new Guest(seededGuestId, null, null, null, null, null, null);
        Room roomRef = new Room(seededRoomId, seededHotelId, 110, 1, true, null);
        Booking booking = new Booking(
                null,
                LocalDate.of(2025, 6, 15),
                LocalDate.of(2025, 6, 17),
                LocalDateTime.now(),
                false,
                guestRef,
                roomRef
        );

        Booking created = bookingServiceFromFactory.create(booking);
        bookingIdsToCleanup.add(created.getId());
        Booking found = bookingServiceFromFactory.findById(created.getId());

        Assert.assertNotNull(found,
                "BookingService from factory should read a booking persisted via create()");
        Assert.assertEquals(found.getGuest().getId(), seededGuestId,
                "Booking guest id from factory service should match seeded guest");
    }

    @Test
    public void factoryHotelServiceFindByIdShouldReturnPersistedAddress() {
        HotelService hotelServiceFromFactory = factory.createHotelService();
        Hotel hotel = createHotel("Factory Address Hotel");
        hotelIdsToCleanup.add(hotel.getId());

        Hotel found = hotelServiceFromFactory.findById(hotel.getId());

        Assert.assertEquals(found.getAddress(), hotel.getAddress(),
                "Factory-created HotelService should return persisted hotel address");
    }

    @Test
    public void factoryBookingServiceCreateShouldReturnGeneratedId() {
        BookingService bookingServiceFromFactory = factory.createBookingService();
        Guest guestRef = new Guest(seededGuestId, null, null, null, null, null, null);
        Room roomRef = new Room(seededRoomId, seededHotelId, 110, 1, true, null);
        Booking booking = new Booking(
                null,
                LocalDate.of(2025, 7, 1),
                LocalDate.of(2025, 7, 2),
                LocalDateTime.now(),
                true,
                guestRef,
                roomRef
        );

        Booking created = bookingServiceFromFactory.create(booking);
        bookingIdsToCleanup.add(created.getId());

        Assert.assertNotNull(created.getId(),
                "BookingService from factory should return booking with generated id after create()");
    }

    @Test
    public void factoryShouldCreateDistinctServiceInstances() {
        HotelService firstHotelService = factory.createHotelService();
        HotelService secondHotelService = factory.createHotelService();
        BookingService firstBookingService = factory.createBookingService();
        BookingService secondBookingService = factory.createBookingService();

        Assert.assertNotSame(firstHotelService, secondHotelService,
                "Each createHotelService() call should return a new instance");
        Assert.assertNotSame(firstBookingService, secondBookingService,
                "Each createBookingService() call should return a new instance");
    }
}
