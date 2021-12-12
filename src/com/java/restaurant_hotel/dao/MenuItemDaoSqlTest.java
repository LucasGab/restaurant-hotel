package com.java.restaurant_hotel.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.java.restaurant_hotel.model.MenuItem;
import com.java.restaurant_hotel.util.DateUtil;

public class MenuItemDaoSqlTest {
    public static void main(String[] args) {
        try {
            ConnectionHandler.migrateDB();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println(e);
            return;
        }
        testGetMenuAddItem();
        testGetMenuItemListAdmin();
        testGetMenuItemListCustomer();
        testModifyMenuItem();
        try {
            ConnectionHandler.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public static void testGetMenuAddItem() {
        MenuItemDao menuItemDao = new MenuItemDaoSql();
        menuItemDao.addMenuItem(new MenuItem(15, "Item added1",(float) 100.0, true, new Date(), "Category 1", false));
        menuItemDao.addMenuItem(new MenuItem(1, "Item added2",(float) 100.0, true, DateUtil.convertToDate("05/10/2022"), "Category 2", false));
        menuItemDao.addMenuItem(new MenuItem(4, "Item added3",(float) 100.0, true, DateUtil.convertToDate("05/10/2021"), "Category 3", false));
        menuItemDao.addMenuItem(new MenuItem(3, "Item added3",(float) 100.0, false, DateUtil.convertToDate("05/10/2021"), "Category 3", false));
        MenuItemDaoSqlTest.testGetMenuItem(15);
        MenuItemDaoSqlTest.testGetMenuItem(1);
        MenuItemDaoSqlTest.testGetMenuItem(4);
        MenuItemDaoSqlTest.testGetMenuItem(3);
    }

    public static void testGetMenuItemListAdmin() {
        MenuItemDao menuItemDao = new MenuItemDaoSql();
        List<MenuItem> menuItemList = menuItemDao.getMenuItemListAdmin();
        for (MenuItem item: menuItemList) {
            System.out.println(item);
        }
    }

    public static void testGetMenuItemListCustomer() {
        MenuItemDao menuItemDao = new MenuItemDaoSql();
        List<MenuItem> menuItemList = menuItemDao.getMenuItemListCustomer();
        for (MenuItem item: menuItemList) {
            System.out.println(item);
        }
    }

    public static void testModifyMenuItem() {
        MenuItem menuItem = new MenuItem(1, "Item 1",15, true, new Date() , "Category 2", true);
        MenuItemDao menuItemDao = new MenuItemDaoSql();
        menuItemDao.modifyMenuItem(menuItem);
        System.out.println(menuItemDao.getMenuItem(1));
    }

    public static void testGetMenuItem(long id) {
        MenuItemDao menuItemDao = new MenuItemDaoSql();
        System.out.println(menuItemDao.getMenuItem(id));
    }
}
