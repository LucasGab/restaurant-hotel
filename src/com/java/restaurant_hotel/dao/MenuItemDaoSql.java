package com.java.restaurant_hotel.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.java.restaurant_hotel.model.MenuItem;

public class MenuItemDaoSql implements MenuItemDao {

    @Override
    public void addMenuItem(MenuItem menuItem) {
        String sqlStatement = "INSERT INTO MenuItems(id, name, price, active, dateOfLaunch, category, freeDelivery) " + 
                                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = ConnectionHandler.getConnection();
            pstm = con.prepareStatement(sqlStatement);
            pstm.setLong(1, menuItem.getId());
            pstm.setString(2, menuItem.getName());
            pstm.setFloat(3, menuItem.getPrice());
            pstm.setBoolean(4, menuItem.getActive());
            pstm.setDate(5, new Date(menuItem.getDateOfLaunch().getTime()));
            pstm.setString(6, menuItem.getCategory());
            pstm.setBoolean(7, menuItem.getFreeDelivery());
            pstm.execute();
            pstm.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    @Override
    public List<MenuItem> getMenuItemListAdmin() {
        String sqlStatement = "SELECT * FROM MenuItems";
        List<MenuItem> menuItemList = null;
        try {
            ResultSet result = ConnectionHandler.executeQueryStatement(sqlStatement);
            menuItemList = this.populateListWithResult(result);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return menuItemList;
    }

    @Override
    public List<MenuItem> getMenuItemListCustomer() {
        String sqlStatement = "SELECT * FROM MenuItems WHERE active=true AND dateOfLaunch <= CURDATE()";
        List<MenuItem> menuItemList = null;
        try {
            ResultSet result = ConnectionHandler.executeQueryStatement(sqlStatement);
            menuItemList = this.populateListWithResult(result);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return menuItemList;
    }

    @Override
    public void modifyMenuItem(MenuItem menuItem) {
        String sqlStatement = "UPDATE MenuItems SET name=?, price=?, active=?, dateOfLaunch=?, category=?, freeDelivery=? " + 
                                "WHERE id=?";
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = ConnectionHandler.getConnection();
            pstm = con.prepareStatement(sqlStatement);
            pstm.setString(1, menuItem.getName());
            pstm.setFloat(2, menuItem.getPrice());
            pstm.setBoolean(3, menuItem.getActive());
            pstm.setDate(4, new Date(menuItem.getDateOfLaunch().getTime()));
            pstm.setString(5, menuItem.getCategory());
            pstm.setBoolean(6, menuItem.getFreeDelivery());
            pstm.setLong(7, menuItem.getId());
            pstm.execute();
            pstm.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    @Override
    public MenuItem getMenuItem(long menuItemId) {
        String sqlStatement = "SELECT * FROM MenuItems WHERE id=" + menuItemId;
        List<MenuItem> menuItemList = null;
        try {
            ResultSet result = ConnectionHandler.executeQueryStatement(sqlStatement);
            menuItemList = this.populateListWithResult(result);
            if (!menuItemList.isEmpty()) return menuItemList.get(0);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return null;
    }

    private List<MenuItem> populateListWithResult(ResultSet result) {
        List<MenuItem> menuItemList = new ArrayList<>();
        MenuItem menuItem = null;
        try {
            while(result.next()) {
                menuItem = new MenuItem(result.getInt(1),result.getString(2),result.getFloat(3),result.getBoolean(4),result.getDate(5),result.getString(6),result.getBoolean(7));
                menuItemList.add(menuItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        return menuItemList;
    }
}
