package com.tomasdutkus.movierental.pricing;

public class DiscountedRentalPricing implements RentalPricing {

    private int discountedDays;
    private RentalPricing rentalPricing;

    public DiscountedRentalPricing(int discountedDays, RentalPricing rentalPricing) {
        this.discountedDays = discountedDays;
        this.rentalPricing = rentalPricing;
    }

    @Override
    public double getRentalPricing(long days) {
        if (days > this.discountedDays) {
            days = days - discountedDays + 1;
        } else {
            days = 1;
        }
        return rentalPricing.getRentalPricing(days);
    }

    @Override
    public double getReturnPricing(long days) {
        return rentalPricing.getRentalPricing(days);
    }
}
