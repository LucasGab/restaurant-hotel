package com.java.restaurant_hotel.dao;

public class CartEmptyException extends Exception {
    public CartEmptyException(String message) {
        super(message);
    }
}
