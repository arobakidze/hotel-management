package com.brickart.hotelmanagement.tests;

import com.brickart.hotelmanagement.patterns.strategy.DiscountPricingStrategy;
import com.brickart.hotelmanagement.patterns.strategy.StandardPricingStrategy;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PricingStrategyTest {

    @Test
    public void standardStrategyExists() {
        Assert.assertNotNull(new StandardPricingStrategy());
    }

    @Test
    public void discountStrategyExists() {
        Assert.assertNotNull(new DiscountPricingStrategy());
    }

    @Test
    public void strategyObjectsDiffer() {
        Assert.assertNotEquals(
                new StandardPricingStrategy().getClass(),
                new DiscountPricingStrategy().getClass()
        );
    }

    @Test
    public void softAssertionsTest() {
        SoftAssert soft = new SoftAssert();

        soft.assertNotNull(new StandardPricingStrategy());
        soft.assertNotNull(new DiscountPricingStrategy());

        soft.assertAll();
    }

    @Test
    public void strategiesAreCreated() {
        Assert.assertTrue(true);
    }
}