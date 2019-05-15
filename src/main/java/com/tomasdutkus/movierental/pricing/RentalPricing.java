package com.tomasdutkus.movierental.pricing;

public interface RentalPricing {

    double getRentalPricing(long days);

    double getReturnPricing(long days);
}