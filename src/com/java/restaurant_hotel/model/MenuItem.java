package com.java.restaurant_hotel.model;

import java.util.Date;
import java.util.Objects;

public class MenuItem {
    private long id;
    private String name;
    private float price;
    private boolean active;
    private Date dateOfLaunch;
    private String category;
    private boolean freeDelivery;


    public MenuItem(long id, String name,float price, boolean active, Date dateOfLaunch, String category, boolean freeDelivery) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.active = active;
        this.dateOfLaunch = dateOfLaunch;
        this.category = category;
        this.freeDelivery = freeDelivery;
    }


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getDateOfLaunch() {
        return this.dateOfLaunch;
    }

    public void setDateOfLaunch(Date dateOfLaunch) {
        this.dateOfLaunch = dateOfLaunch;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isFreeDelivery() {
        return this.freeDelivery;
    }

    public boolean getFreeDelivery() {
        return this.freeDelivery;
    }

    public void setFreeDelivery(boolean freeDelivery) {
        this.freeDelivery = freeDelivery;
    }

    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "[Id: "+ this.id +", Name: " + this.name +", Price: " + this.price + ", Active: " + this.active + ", Date of Launch: " + this.dateOfLaunch + ", Category: " + this.category + ", Free Deliver: " + this.freeDelivery + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof MenuItem)) {
            return false;
        }
        MenuItem menuItem = (MenuItem) o;
        return this.id == menuItem.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.active, this.dateOfLaunch, this.category, this.freeDelivery);
    }
}
