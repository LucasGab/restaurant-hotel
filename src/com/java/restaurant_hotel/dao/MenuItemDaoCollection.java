package com.java.restaurant_hotel.dao;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.java.restaurant_hotel.model.MenuItem;

public class MenuItemDaoCollection implements MenuItemDao {

	private static List<MenuItem> menuItemList;

    public MenuItemDaoCollection() {
		if(MenuItemDaoCollection.menuItemList == null) {
			MenuItemDaoCollection.menuItemList = new ArrayList<>();
			LocalDateTime localDateOfLaunch = LocalDateTime.now();
			for (int i = 0;i < 10;i++) {
				LocalDateTime ldtm = i%2==0?localDateOfLaunch.plusDays(i):localDateOfLaunch.minusDays(i);
				Date dateOfLaunch = Date.from(ldtm.atZone(ZoneId.systemDefault()).toInstant());
				this.addMenuItem(new MenuItem(i, "Item " + i,(float)i * 10, i%3==0, dateOfLaunch , "Category " + i, i%2!=0));
			}
		}
	}
	
	@Override
	public void addMenuItem(MenuItem menuItem) {
		MenuItemDaoCollection.menuItemList.add(menuItem);
	}

	@Override
	public List<MenuItem> getMenuItemListAdmin() {
		return menuItemList;
	}

	@Override
	public List<MenuItem> getMenuItemListCustomer() {
		List<MenuItem> itensList = new ArrayList<>();
		Date today = new Date();
		for(MenuItem item: MenuItemDaoCollection.menuItemList) {
			if ((item.getDateOfLaunch().before(today) || item.getDateOfLaunch().equals(today)) && item.getActive()) {
				itensList.add(item);
			}
		}
		return itensList;
	}

	@Override
	public void modifyMenuItem(MenuItem menuItem) {
		for (int i = 0;i < MenuItemDaoCollection.menuItemList.size();i++) {
			try {
				MenuItem actualItem = MenuItemDaoCollection.menuItemList.get(i);
				if (menuItem.getId() == actualItem.getId()) {
					MenuItemDaoCollection.menuItemList.set(i, menuItem);
					break;
				}
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
				System.out.println("Index out of bounds");
			}
		}
	}

	@Override
	public MenuItem getMenuItem(long menuItemId) {
		for (int i = 0;i < MenuItemDaoCollection.menuItemList.size();i++) {
			try {
				MenuItem actualItem = MenuItemDaoCollection.menuItemList.get(i);
				if (actualItem.getId() == menuItemId) {
					return actualItem;
				}
			} catch (IndexOutOfBoundsException e) {
				e.printStackTrace();
				System.out.println("Index out of bounds");
			}
		}
		return null;
	}
}
