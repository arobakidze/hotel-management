package com.brickart.hotelmanagement.tests;

import com.brickart.hotelmanagement.domain.Department;
import com.brickart.hotelmanagement.domain.Hotel;
import com.brickart.hotelmanagement.domain.Room;
import com.brickart.hotelmanagement.domain.RoomType;
import com.brickart.hotelmanagement.patterns.builder.HotelBuilder;
import com.brickart.hotelmanagement.tests.support.IntegrationTestBase;
import org.testng.Assert;
import org.testng.annotations.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HotelServiceTest extends IntegrationTestBase {

    private final List<Long> hotelIdsToCleanup = new ArrayList<>();
    private Long roomTypeIdToCleanup;

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Before Suite - HotelServiceTest");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("Before Test - HotelServiceTest");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("Before Class - HotelServiceTest");
        roomTypeIdToCleanup = insertRoomType("Suite", 2, new BigDecimal("150.00"));
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Before Method - HotelServiceTest");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("After Method - HotelServiceTest");
    }

    @AfterClass
    public void afterClass() {
        for (Long hotelId : hotelIdsToCleanup) {
            cleanupHotel(hotelId);
        }
        if (roomTypeIdToCleanup != null) {
            cleanupRoomType(roomTypeIdToCleanup);
        }
        System.out.println("After Class - HotelServiceTest");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("After Test - HotelServiceTest");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("After Suite - HotelServiceTest");
    }

    @Test
    public void createHotelThenFindByIdShouldReturnMatchingFields() {
        Hotel hotel = createHotel("Grand Hotel Test");
        hotelIdsToCleanup.add(hotel.getId());

        Hotel found = hotelService.findById(hotel.getId());

        Assert.assertNotNull(found, "findById should return a hotel after create()");
        Assert.assertEquals(found.getName(), hotel.getName(),
                "Hotel name from findById should match value passed to create()");
        Assert.assertEquals(found.getAddress(), hotel.getAddress(),
                "Hotel address from findById should match value passed to create()");
        Assert.assertEquals(found.getPhone(), hotel.getPhone(),
                "Hotel phone from findById should match value passed to create()");
        Assert.assertEquals(found.getStarRating(), hotel.getStarRating(),
                "Hotel star rating from findById should match value passed to create()");
    }

    @Test
    public void updateHotelShouldPersistChangedFields() {
        Hotel hotel = createHotel("Update Hotel");
        hotelIdsToCleanup.add(hotel.getId());

        Hotel updated = new Hotel(
                hotel.getId(),
                "Renamed Hotel",
                "New Address 42",
                "999-UPDATED",
                5
        );
        hotelService.update(updated);

        Hotel found = hotelService.findById(hotel.getId());
        Assert.assertEquals(found.getName(), "Renamed Hotel",
                "Hotel name should reflect update() changes");
        Assert.assertEquals(found.getAddress(), "New Address 42",
                "Hotel address should reflect update() changes");
        Assert.assertEquals(found.getStarRating(), 5,
                "Hotel star rating should reflect update() changes");
    }

    @Test
    public void deleteHotelShouldRemoveRecordFromDatabase() {
        Hotel hotel = createHotel("Delete Hotel");
        Long hotelId = hotel.getId();

        hotelService.delete(hotelId);

        Hotel found = hotelService.findById(hotelId);
        Assert.assertNull(found, "findById should return null after delete()");
    }

    @Test
    public void findAllShouldIncludeCreatedHotel() {
        Hotel hotel = createHotel("FindAll Hotel");
        hotelIdsToCleanup.add(hotel.getId());

        List<Hotel> hotels = hotelService.findAll();
        boolean containsCreated = hotels.stream()
                .anyMatch(h -> h.getId().equals(hotel.getId()));

        Assert.assertTrue(containsCreated,
                "findAll() should include the hotel created in this test");
    }

    @Test
    public void createHotelWithDetailsShouldPersistHotelDepartmentsAndRooms() {
        RoomType roomType = new RoomType(roomTypeIdToCleanup, "Suite", 2, new BigDecimal("150.00"));
        Room room = new Room(null, null, 201, 2, true, roomType);
        Department department = new Department(null, null, "Reception", "Ground Floor");

        Hotel hotel = new HotelBuilder()
                .name("Builder Hotel " + uniqueSuffix())
                .address("Builder Street 1")
                .phone("555-BUILD")
                .starRating(4)
                .addDepartment(department)
                .addRoom(room)
                .build();

        hotelService.createHotelWithDetails(hotel);
        hotelIdsToCleanup.add(hotel.getId());

        Hotel found = hotelService.findById(hotel.getId());
        Assert.assertEquals(found.getName(), hotel.getName(),
                "Hotel created via builder should be readable by findById()");
        Assert.assertNotNull(hotel.getDepartments(), "Departments list should be set on hotel after createHotelWithDetails()");
        Assert.assertEquals(hotel.getDepartments().size(), 1,
                "createHotelWithDetails() should persist one department");
        Assert.assertNotNull(hotel.getRooms(), "Rooms list should be set on hotel after createHotelWithDetails()");
        Assert.assertEquals(hotel.getRooms().size(), 1,
                "createHotelWithDetails() should persist one room");
    }

    @Test
    public void createHotelShouldAssignGeneratedId() {
        Hotel hotel = createHotel("Id Hotel");
        hotelIdsToCleanup.add(hotel.getId());

        Assert.assertNotNull(hotel.getId(),
                "create() should assign a generated database id to the hotel");
        Assert.assertTrue(hotel.getId() > 0,
                "Generated hotel id should be a positive number");
    }
}
