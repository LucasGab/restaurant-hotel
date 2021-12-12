package com.java.restaurant_hotel.dao;

public class CartDaoCollectionTest {
    public static void main(String[] args) {
        testAddCartItem();
        testRemoveCartItem();
    }

    public static void testAddCartItem() {
        CartDao cartDao = new CartDaoCollection();
        cartDao.addCartItem(1, 1);
        try {
			System.out.println(cartDao.getAllCartItems(1));
		} catch (CartEmptyException e) {
			e.printStackTrace();
            System.out.println("Empty Cart");
		}
    }

    public static void testGetAllCartItems() {
        CartDao cartDao = new CartDaoCollection();
        try {
			System.out.println(cartDao.getAllCartItems(1));
		} catch (CartEmptyException e) {
			e.printStackTrace();
            System.out.println("Empty Cart");
		}
    }

    public static void testRemoveCartItem() {
        CartDao cartDao = new CartDaoCollection();
        cartDao.removeCartItem(1, 1);
        try {
			System.out.println(cartDao.getAllCartItems(1));
		} catch (CartEmptyException e) {
			e.printStackTrace();
            System.out.println("Empty Cart");
		}
    }
}
