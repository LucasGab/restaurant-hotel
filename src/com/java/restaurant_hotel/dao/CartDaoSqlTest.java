package com.java.restaurant_hotel.dao;

import java.sql.SQLException;

public class CartDaoSqlTest {
    public static void main(String[] args) {
        try {
            ConnectionHandler.migrateDB();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println(e);
            return;
        }
        testAddCartItem();
        testRemoveCartItem();
        try {
            ConnectionHandler.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public static void testAddCartItem() {
        CartDao cartDao = new CartDaoSql();
        cartDao.addCartItem(1, 1);
        try {
			System.out.println(cartDao.getAllCartItems(1));
		} catch (CartEmptyException e) {
			e.printStackTrace();
            System.out.println("Empty Cart");
		}
    }

    public static void testGetAllCartItems() {
        CartDao cartDao = new CartDaoSql();
        try {
			System.out.println(cartDao.getAllCartItems(1));
		} catch (CartEmptyException e) {
			e.printStackTrace();
            System.out.println("Empty Cart");
		}
    }

    public static void testRemoveCartItem() {
        CartDao cartDao = new CartDaoSql();
        cartDao.removeCartItem(1, 1);
        try {
			System.out.println(cartDao.getAllCartItems(1));
		} catch (CartEmptyException e) {
			e.printStackTrace();
            System.out.println("Empty Cart");
		}
    }
}
