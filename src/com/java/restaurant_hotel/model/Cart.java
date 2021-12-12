package com.java.restaurant_hotel.model;

import java.util.List;
import java.util.Objects;

public class Cart {
    private List<MenuItem> menuItemList;
    private double total;

    public Cart(List<MenuItem> menuItemList, double total) {
        this.menuItemList = menuItemList;
        this.total = total;
    }

    public List<MenuItem> getMenuItemList() {
        return this.menuItemList;
    }

    public void setMenuItemList(List<MenuItem> menuItemList) {
        this.menuItemList = menuItemList;
    }

    public double getTotal() {
        return this.total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        StringBuilder cartStr = new StringBuilder();
        cartStr.append("Cart Init");
        for(MenuItem item: this.menuItemList) {
            cartStr.append("\n"+item);
        }
        cartStr.append("Cart End");
        return cartStr.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Cart)) {
            return false;
        }
        Cart cart = (Cart) o;
        return Objects.equals(menuItemList, cart.menuItemList) && total == cart.total;
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuItemList, total);
    }
    
}
