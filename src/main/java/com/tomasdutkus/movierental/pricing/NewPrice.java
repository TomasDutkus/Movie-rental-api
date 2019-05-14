package com.tomasdutkus.movierental.pricing;

public class NewPrice implements RentalPricing{

    @Override
    public double getRentalPricing(long days) {

        return 0;
    }

    @Override
    public double getReturnPricing(long days) {
        return 0;
    }
}
