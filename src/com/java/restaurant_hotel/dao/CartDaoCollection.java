package com.java.restaurant_hotel.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.java.restaurant_hotel.model.Cart;
import com.java.restaurant_hotel.model.MenuItem;

public class CartDaoCollection implements CartDao {
    private static HashMap<Long,Cart> userCarts;

    public CartDaoCollection() {
        if (CartDaoCollection.userCarts == null) {
            CartDaoCollection.userCarts = new HashMap<>();
        }
    }

    @Override
    public void addCartItem(long userId, long menuItemId) {
        MenuItemDao menuItemDao = new MenuItemDaoCollection();
        MenuItem menuItem = menuItemDao.getMenuItem(menuItemId);
        if(menuItem == null) return;

        Cart cart;
        if (CartDaoCollection.userCarts.containsKey(userId)) {
            cart = CartDaoCollection.userCarts.get(userId);
        } else {
            cart = new Cart(new ArrayList<>(), 0);
            CartDaoCollection.userCarts.put(userId, cart);
        }
        List<MenuItem> menuItemList = cart.getMenuItemList();
        menuItemList.add(menuItem);
    }
    
    @Override
    public List<MenuItem> getAllCartItems(long userId) throws CartEmptyException {
        if (CartDaoCollection.userCarts.containsKey(userId)) {
            Cart cart = CartDaoCollection.userCarts.get(userId);
            List<MenuItem> menuItemList = cart.getMenuItemList();
            if (menuItemList.isEmpty()) throw new CartEmptyException("The cart is empty");
            double total = 0;
            for (MenuItem item: menuItemList) {
                total += item.getPrice();
            }
            cart.setTotal(total);
            return cart.getMenuItemList();
        } else {
            throw new CartEmptyException("The user doesnt have a cart");
        }
    }
    
    @Override
    public void removeCartItem(long userId, long menuItemId) {
        if (CartDaoCollection.userCarts.containsKey(userId)) {
            Cart cart = CartDaoCollection.userCarts.get(userId);
            List<MenuItem> menuItemList = cart.getMenuItemList();
            for(MenuItem item: menuItemList) {
                if(item.getId() == menuItemId) {
                    menuItemList.remove(item);
                    break;
                }
            }
        }
    }


}

