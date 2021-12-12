package com.java.restaurant_hotel.dao;

import java.util.Date;
import java.util.List;

import com.java.restaurant_hotel.model.MenuItem;

public class MenuItemDaoCollectionTest {
    public static void main(String[] args) {
        testGetMenuAddItem();
        testGetMenuItemListAdmin();
        testGetMenuItemListCustomer();
        testModifyMenuItem();
    }

    public static void testGetMenuAddItem() {
        MenuItemDao menuItemDao = new MenuItemDaoCollection();
        menuItemDao.addMenuItem(new MenuItem(15, "Item added",(float) 100.0, false, new Date(), "Category 1", false));
        MenuItemDaoCollectionTest.testGetMenuItem(15);
    }

    public static void testGetMenuItemListAdmin() {
        MenuItemDao menuItemDao = new MenuItemDaoCollection();
        List<MenuItem> menuItemList = menuItemDao.getMenuItemListAdmin();
        for (MenuItem item: menuItemList) {
            System.out.println(item);
        }
    }

    public static void testGetMenuItemListCustomer() {
        MenuItemDao menuItemDao = new MenuItemDaoCollection();
        List<MenuItem> menuItemList = menuItemDao.getMenuItemListCustomer();
        for (MenuItem item: menuItemList) {
            System.out.println(item);
        }
    }

    public static void testModifyMenuItem() {
        MenuItem menuItem = new MenuItem(1, "Item 1",15, true, new Date() , "Category 2", true);
        MenuItemDao menuItemDao = new MenuItemDaoCollection();
        menuItemDao.modifyMenuItem(menuItem);
        System.out.println(menuItemDao.getMenuItem(1));
    }

    public static void testGetMenuItem(long id) {
        MenuItemDao menuItemDao = new MenuItemDaoCollection();
        System.out.println(menuItemDao.getMenuItem(id));
    }
}
