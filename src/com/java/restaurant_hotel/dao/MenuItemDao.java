package com.java.restaurant_hotel.dao;

import java.util.List;

import com.java.restaurant_hotel.model.MenuItem;

public interface MenuItemDao {
    public void addMenuItem(MenuItem menuItem);
    public List<MenuItem> getMenuItemListAdmin();
    public List<MenuItem> getMenuItemListCustomer();
    public void modifyMenuItem(MenuItem menuItem);
    public MenuItem getMenuItem(long menuItemId);
}
