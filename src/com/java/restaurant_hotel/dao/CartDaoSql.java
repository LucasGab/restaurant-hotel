package com.java.restaurant_hotel.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.java.restaurant_hotel.model.Cart;
import com.java.restaurant_hotel.model.MenuItem;

public class CartDaoSql implements CartDao {

    @Override
    public void addCartItem(long userId, long menuItemId) {
        MenuItemDao menuItemDao = new MenuItemDaoSql(); 
        MenuItem menuItem = menuItemDao.getMenuItem(menuItemId);
        if (menuItem == null) return;
        this.getCart(userId);
        this.addToCart(userId, menuItemId);
    }

    @Override
    public List<MenuItem> getAllCartItems(long userId) throws CartEmptyException {
        Cart cart = this.getCart(userId);
        List<MenuItem> menuItemList = cart.getMenuItemList();
        if (menuItemList.isEmpty()) {
            this.addTotalToCart((float)0,userId);
            throw new CartEmptyException("The cart is empty");
        } 
        double total = 0;
        for (MenuItem item: menuItemList) {
            total += item.getPrice();
        }
        this.addTotalToCart((float)total,userId);
        cart.setTotal(total);
        return cart.getMenuItemList();
    }

    @Override
    public void removeCartItem(long userId, long menuItemId) {
        String sqlStatement = "DELETE FROM ListItems WHERE userID=" + userId + " AND menuID=" + menuItemId + " LIMIT 1";
        try {
            ConnectionHandler.executeUpdateStatement(sqlStatement);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.err.println(e);
        }
    }

    private Cart getCart(long userId) {
        String sqlStatement = "SELECT * FROM Carts WHERE userID=" + userId;
        long id = -1;
        float total = 0;
        List<Long> itemsId = new ArrayList<>();
        MenuItemDao menuItemDao = new MenuItemDaoSql(); 
        MenuItem menuItem = null;
        Cart cart = new Cart(new ArrayList<>(), 0);
        try {
            ResultSet result = ConnectionHandler.executeQueryStatement(sqlStatement);
            while(result.next()) {
                id = result.getLong(1);
                total = result.getFloat(3);
                cart.setTotal(total);
                break;
            }
            
            if (id == -1) {
                this.createCart(userId);
            }
            sqlStatement = "SELECT * FROM ListItems WHERE userID=" + userId;
            result = ConnectionHandler.executeQueryStatement(sqlStatement);
            while(result.next()) {
                itemsId.add(result.getLong(3));
            }

            for(long idItem: itemsId) {
                menuItem = menuItemDao.getMenuItem(idItem);
                if(menuItem != null) cart.getMenuItemList().add(menuItem);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return cart;
    }

    private void addTotalToCart(float total, long userId) {
        String sqlStatement = "UPDATE Carts SET total=? WHERE userID=?";
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = ConnectionHandler.getConnection();
            pstm = con.prepareStatement(sqlStatement);
            pstm.setFloat(1, total);
            pstm.setLong(2, userId);
            pstm.execute();
            pstm.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    private void addToCart(long userId, long menuId) {
        String sqlStatement = "INSERT INTO ListItems(userID, menuID) VALUES (?, ?)";
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = ConnectionHandler.getConnection();
            pstm = con.prepareStatement(sqlStatement);
            pstm.setLong(1, userId);
            pstm.setFloat(2, menuId);
            pstm.execute();
            pstm.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    private void createCart(long userId) {
        String sqlStatement = "INSERT INTO Carts(userID, total) VALUES (?, ?)";
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = ConnectionHandler.getConnection();
            pstm = con.prepareStatement(sqlStatement);
            pstm.setLong(1, userId);
            pstm.setFloat(2, 0);
            pstm.execute();
            pstm.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
    
}
