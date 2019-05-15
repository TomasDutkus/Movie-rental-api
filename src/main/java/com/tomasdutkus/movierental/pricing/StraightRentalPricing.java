package com.tomasdutkus.movierental.pricing;

public class StraightRentalPricing implements RentalPricing {

    private double amount;

    public StraightRentalPricing(double amount) {
        this.amount = amount;
    }

    @Override
    public double getRentalPricing(long days) {
        return amount * days;
    }

    @Override
    public double getReturnPricing(long days) {
        return getRentalPricing(days);
    }

}
