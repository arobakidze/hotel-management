package com.brickart.hotelmanagement.patterns.strategy;

import com.brickart.hotelmanagement.domain.Booking;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

public class StandardPricingStrategy implements PricingStrategy {

    @Override
    public BigDecimal calculatePrice(Booking booking) {
        long nights = ChronoUnit.DAYS.between(
                booking.getCheckIn(),
                booking.getCheckOut()
        );

        BigDecimal pricePerNight = booking.getRoom()
                .getRoomType()
                .getPricePerNight();

        return pricePerNight.multiply(BigDecimal.valueOf(nights));
    }
}