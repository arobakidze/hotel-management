package com.brickart.hotelmanagement.tests;

import com.brickart.hotelmanagement.domain.Hotel;
import org.testng.Assert;
import org.testng.annotations.*;

public class HotelTest {

    private Hotel hotel;

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Before Suite");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("Before Test");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("Before Class");
    }

    @BeforeMethod
    public void beforeMethod() {
        hotel = new Hotel();
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("After Method");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("After Class");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("After Test");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("After Suite");
    }

    @Test
    public void hotelNotNull() {
        Assert.assertNotNull(hotel);
    }

    @Test
    public void hotelClassName() {
        Assert.assertEquals(
                hotel.getClass().getSimpleName(),
                "Hotel"
        );
    }

    @Test
    public void hotelObjectExists() {
        Assert.assertTrue(hotel instanceof Hotel);
    }

    @Test
    public void secondHotelNotSame() {
        Assert.assertNotSame(
                hotel,
                new Hotel()
        );
    }

    @Test
    public void hotelCreated() {
        Assert.assertNotNull(hotel);
    }
}