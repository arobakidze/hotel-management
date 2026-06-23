package com.brickart.hotelmanagement.tests;

import com.brickart.hotelmanagement.patterns.factory.ServiceFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ServiceFactoryTest {

    @Test
    public void factoryExists() {
        Assert.assertNotNull(new ServiceFactory());
    }

    @Test
    public void factoryClassName() {
        Assert.assertEquals(
                ServiceFactory.class.getSimpleName(),
                "ServiceFactory"
        );
    }

    @Test
    public void factoryPackage() {
        Assert.assertTrue(
                ServiceFactory.class.getPackageName()
                        .contains("factory")
        );
    }

    @Test
    public void factoryObject() {
        Assert.assertTrue(
                new ServiceFactory() instanceof ServiceFactory
        );
    }

    @Test
    public void factoryNotNull() {
        Assert.assertNotNull(new ServiceFactory());
    }
}