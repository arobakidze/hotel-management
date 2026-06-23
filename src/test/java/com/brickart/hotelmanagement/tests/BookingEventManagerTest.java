package com.brickart.hotelmanagement.tests;

import com.brickart.hotelmanagement.patterns.listener.BookingEventManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BookingEventManagerTest {

    @Test
    public void managerCreated() {
        Assert.assertNotNull(new BookingEventManager());
    }

    @Test
    public void managerIsCorrectType() {
        Assert.assertTrue(
                new BookingEventManager()
                        instanceof BookingEventManager
        );
    }

    @Test
    public void managerClassName() {
        Assert.assertEquals(
                BookingEventManager.class.getSimpleName(),
                "BookingEventManager"
        );
    }

    @Test
    public void managerPackageExists() {
        Assert.assertTrue(
                BookingEventManager.class.getPackageName()
                        .contains("listener")
        );
    }

    @Test
    public void managerNotNullAgain() {
        Assert.assertNotNull(new BookingEventManager());
    }
}