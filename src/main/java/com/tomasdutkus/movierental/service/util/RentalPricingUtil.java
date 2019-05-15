package com.tomasdutkus.movierental.service.util;

import com.tomasdutkus.movierental.model.MovieTypes;
import com.tomasdutkus.movierental.model.PriceType;
import com.tomasdutkus.movierental.model.RentalItem;
import com.tomasdutkus.movierental.pricing.DiscountedRentalPricing;
import com.tomasdutkus.movierental.pricing.RentalPricing;
import com.tomasdutkus.movierental.pricing.StraightRentalPricing;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RentalPricingUtil {

    // Rental defined
    private static final RentalPricing PREMIUM_RENTAL = new StraightRentalPricing(PriceType.PREMIUM_PRICE.getPrice());
    private static final RentalPricing BASIC_RENTAL = new StraightRentalPricing(PriceType.BASIC_PRICE.getPrice());

    // Pricing based on rental
    private static final RentalPricing NEW_RELEASES_PRICING = new DiscountedRentalPricing(1, PREMIUM_RENTAL);
    private static final RentalPricing REGULAR_MOVIES_PRICING = new DiscountedRentalPricing(3, BASIC_RENTAL);
    private static final RentalPricing OLD_MOVIES_PRICING = new DiscountedRentalPricing(5, BASIC_RENTAL);

    private RentalPricingUtil() {
        // private constructor
    }

    private static RentalPricing getRentalPricing(MovieTypes movieTypes) {
        switch (movieTypes) {
            case NEW:
                return NEW_RELEASES_PRICING;
            case REGULAR:
                return REGULAR_MOVIES_PRICING;
            case OLD:
                return OLD_MOVIES_PRICING;
            default:
                return REGULAR_MOVIES_PRICING;
        }
    }

    public static Double calculateRentalPricing(RentalItem rentalItem, MovieTypes movieTypes) {
        RentalPricing rentalPricing = getRentalPricing(movieTypes);

        LocalDate itemBookedDate = rentalItem.getBookingdate();
        LocalDate itemReturningDate = rentalItem.getReturningDate();
        long daysRented = ChronoUnit.DAYS.between(itemBookedDate, itemReturningDate);

        return rentalPricing.getRentalPricing(daysRented);
    }

    public static double calculateReturnSurcharge(RentalItem rentalItem, MovieTypes movieTypes) {
        RentalPricing rentalPricing = getRentalPricing(movieTypes);

        LocalDate itemReturningDate = rentalItem.getReturningDate();
        LocalDate itemActualReturnedDate = rentalItem.getActualReturnedDate();
        if (itemActualReturnedDate.isAfter(itemReturningDate)) {
            long daysExtra = Math.abs(ChronoUnit.DAYS.between(itemReturningDate, itemActualReturnedDate));
            return rentalPricing.getReturnPricing(daysExtra);
        }
        return 0;
    }
}
