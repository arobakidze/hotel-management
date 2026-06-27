package com.brickart.hotelmanagement.tests;

import com.brickart.hotelmanagement.domain.Booking;
import com.brickart.hotelmanagement.domain.Guest;
import com.brickart.hotelmanagement.domain.Room;
import com.brickart.hotelmanagement.domain.RoomType;
import com.brickart.hotelmanagement.patterns.strategy.DiscountPricingStrategy;
import com.brickart.hotelmanagement.patterns.strategy.StandardPricingStrategy;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PricingStrategyTest {

    private Booking bookingForThreeNightsAtHundredPerNight() {
        RoomType roomType = new RoomType(1L, "Standard", 2, new BigDecimal("100.00"));
        Room room = new Room(1L, 1L, 101, 1, true, roomType);
        Guest guest = new Guest(
                1L,
                "John",
                "Doe",
                "john@test.com",
                "555-0001",
                "PASS-001",
                LocalDate.of(1990, 5, 20)
        );
        return new Booking(
                null,
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 4),
                LocalDateTime.now(),
                false,
                guest,
                room
        );
    }

    @Test
    public void standardStrategyShouldCalculatePriceForThreeNights() {
        StandardPricingStrategy strategy = new StandardPricingStrategy();
        BigDecimal price = strategy.calculatePrice(bookingForThreeNightsAtHundredPerNight());

        Assert.assertEquals(price, new BigDecimal("300.00"),
                "Standard pricing for 3 nights at 100 per night should be 300.00");
    }

    @Test
    public void standardStrategyShouldCalculatePriceForOneNight() {
        Booking booking = bookingForThreeNightsAtHundredPerNight();
        booking.setCheckOut(LocalDate.of(2025, 6, 2));

        StandardPricingStrategy strategy = new StandardPricingStrategy();
        BigDecimal price = strategy.calculatePrice(booking);

        Assert.assertEquals(price, new BigDecimal("100.00"),
                "Standard pricing for 1 night at 100 per night should be 100.00");
    }

    @Test
    public void discountStrategyShouldApplyDefaultTenPercentDiscount() {
        DiscountPricingStrategy strategy = new DiscountPricingStrategy();
        BigDecimal price = strategy.calculatePrice(bookingForThreeNightsAtHundredPerNight());

        Assert.assertEquals(price, new BigDecimal("270.00"),
                "Default 10% discount on 300.00 should produce 270.00");
    }

    @Test
    public void discountStrategyShouldApplyCustomDiscountPercent() {
        DiscountPricingStrategy strategy = new DiscountPricingStrategy(
                new StandardPricingStrategy(),
                new BigDecimal("20")
        );
        BigDecimal price = strategy.calculatePrice(bookingForThreeNightsAtHundredPerNight());

        Assert.assertEquals(price, new BigDecimal("240.00"),
                "20% discount on 300.00 should produce 240.00");
    }

    @Test
    public void pricingStrategiesShouldCalculateExpectedValuesWithSoftAssertions() {
        Booking booking = bookingForThreeNightsAtHundredPerNight();
        StandardPricingStrategy standard = new StandardPricingStrategy();
        DiscountPricingStrategy discount = new DiscountPricingStrategy();

        SoftAssert soft = new SoftAssert();
        soft.assertEquals(standard.calculatePrice(booking), new BigDecimal("300.00"),
                "Standard strategy should calculate 300.00 for three nights");
        soft.assertEquals(discount.calculatePrice(booking), new BigDecimal("270.00"),
                "Discount strategy should calculate 270.00 for three nights with 10% off");
        soft.assertAll();
    }
}
