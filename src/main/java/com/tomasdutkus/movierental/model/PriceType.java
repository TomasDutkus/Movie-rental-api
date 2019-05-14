package com.tomasdutkus.movierental.model;

public enum PriceType {

    PREMIUM_PRICE(50.00), BASIC_PRICE(35.00);

    private final double price;

    private PriceType(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
