package com.java.restaurant_hotel.dao;

import java.util.List;

import com.java.restaurant_hotel.model.MenuItem;

public interface CartDao {
    public void addCartItem(long userId, long menuItemId);
    public List<MenuItem> getAllCartItems(long userId) throws CartEmptyException;
    public void removeCartItem(long userId, long menuItemId);
}
