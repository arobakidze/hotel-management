package com.brickart.hotelmanagement.tests;

import com.brickart.hotelmanagement.domain.Hotel;
import com.brickart.hotelmanagement.listeners.TestListener;
import com.brickart.hotelmanagement.patterns.builder.HotelBuilder;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(TestListener.class)
public class HotelBuilderTest {

    @Test
    public void testBuilderCreatesHotel() {
        Hotel hotel = new HotelBuilder().build();
        Assert.assertNotNull(hotel);
    }

    @Test
    public void testBuilderCreatesDifferentObjects() {
        Hotel h1 = new HotelBuilder().build();
        Hotel h2 = new HotelBuilder().build();
        Assert.assertNotSame(h1, h2);
    }

    @Test
    public void testHotelObjectExists() {
        Assert.assertTrue(new HotelBuilder().build() instanceof Hotel);
    }

    @Test
    public void testBuilderNotNull() {
        Assert.assertNotNull(new HotelBuilder());
    }

    @Test
    public void testHotelIsCreated() {
        Hotel hotel = new HotelBuilder().build();
        Assert.assertNotNull(hotel);
    }
}