package com.brickart.hotelmanagement.patterns.strategy;

import com.brickart.hotelmanagement.domain.Booking;

import java.math.BigDecimal;

public class DiscountPricingStrategy implements PricingStrategy {

    private final PricingStrategy baseStrategy;
    private final BigDecimal discountPercent;

    public DiscountPricingStrategy(PricingStrategy baseStrategy, BigDecimal discountPercent) {
        this.baseStrategy = baseStrategy;
        this.discountPercent = discountPercent;
    }

    @Override
    public BigDecimal calculatePrice(Booking booking) {
        BigDecimal originalPrice = baseStrategy.calculatePrice(booking);

        BigDecimal discountAmount = originalPrice
                .multiply(discountPercent)
                .divide(BigDecimal.valueOf(100));

        return originalPrice.subtract(discountAmount);
    }
}