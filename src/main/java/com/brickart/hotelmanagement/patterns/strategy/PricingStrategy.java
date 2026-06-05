package com.brickart.hotelmanagement.patterns.strategy;

import com.brickart.hotelmanagement.domain.Booking;

import java.math.BigDecimal;

public interface PricingStrategy {

    BigDecimal calculatePrice(Booking booking);
}