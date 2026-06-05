package com.brickart.hotelmanagement;

import com.brickart.hotelmanagement.domain.Booking;
import com.brickart.hotelmanagement.domain.Hotel;
import com.brickart.hotelmanagement.patterns.abstractfactory.MyBatisPersistenceFactory;
import com.brickart.hotelmanagement.patterns.abstractfactory.PersistenceFactory;
import com.brickart.hotelmanagement.patterns.builder.HotelBuilder;
import com.brickart.hotelmanagement.patterns.decorator.LoggingBookingServiceDecorator;
import com.brickart.hotelmanagement.patterns.facade.HotelManagementFacade;
import com.brickart.hotelmanagement.patterns.factory.ServiceFactory;
import com.brickart.hotelmanagement.patterns.listener.BookingEventManager;
import com.brickart.hotelmanagement.patterns.listener.BookingLoggerListener;
import com.brickart.hotelmanagement.patterns.strategy.DiscountPricingStrategy;
import com.brickart.hotelmanagement.patterns.strategy.PricingStrategy;
import com.brickart.hotelmanagement.patterns.strategy.StandardPricingStrategy;
import com.brickart.hotelmanagement.service.BookingService;
import com.brickart.hotelmanagement.service.HotelService;

import java.math.BigDecimal;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        ServiceFactory serviceFactory = new ServiceFactory();

        HotelService hotelService = serviceFactory.createHotelService();
        BookingService bookingService = serviceFactory.createBookingService();

        BookingService decoratedBookingService =
                new LoggingBookingServiceDecorator(bookingService);

        HotelManagementFacade facade =
                new HotelManagementFacade(hotelService, decoratedBookingService);

        PersistenceFactory persistenceFactory = new MyBatisPersistenceFactory();
        HotelService myBatisHotelService = persistenceFactory.createHotelService();

        Hotel exampleHotel = new HotelBuilder()
                .name("Builder Hotel")
                .address("Tbilisi")
                .phone("555123456")
                .starRating(5)
                .build();

        System.out.println("Builder created hotel example: " + exampleHotel.getName());

        BookingEventManager eventManager = new BookingEventManager();
        eventManager.addListener(new BookingLoggerListener());

        System.out.println("Hotels:");
        List<Hotel> hotels = facade.getAllHotels();

        hotels.forEach(h -> System.out.println(
                h.getName() + " - " + h.getStarRating() + " stars"
        ));

        System.out.println();

        System.out.println("Bookings:");
        List<Booking> bookings = facade.getAllBookingsWithDetails();

        bookings.forEach(b -> System.out.println(
                b.getGuest().getFirstName() + " " + b.getGuest().getLastName() +
                        " -> Room " + b.getRoom().getRoomNumber() +
                        " | " + b.getCheckIn() + " to " + b.getCheckOut()
        ));

        if (!bookings.isEmpty()) {
            Booking firstBooking = bookings.get(0);

            PricingStrategy standardPricing = new StandardPricingStrategy();
            PricingStrategy discountPricing =
                    new DiscountPricingStrategy(standardPricing, BigDecimal.valueOf(10));

            BigDecimal standardPrice = standardPricing.calculatePrice(firstBooking);
            BigDecimal discountedPrice = discountPricing.calculatePrice(firstBooking);

            System.out.println();
            System.out.println("Standard price for first booking: " + standardPrice);
            System.out.println("Discounted price for first booking: " + discountedPrice);

            eventManager.notifyBookingCreated(firstBooking);
        }

        System.out.println();
        System.out.println("Patterns demonstration completed successfully.");
    }
}