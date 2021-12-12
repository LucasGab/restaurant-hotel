import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.java.restaurant_hotel.dao.CartDao;
import com.java.restaurant_hotel.dao.CartDaoSql;
import com.java.restaurant_hotel.dao.CartEmptyException;
import com.java.restaurant_hotel.dao.ConnectionHandler;
import com.java.restaurant_hotel.dao.MenuItemDao;
import com.java.restaurant_hotel.dao.MenuItemDaoSql;
import com.java.restaurant_hotel.model.MenuItem;
import com.java.restaurant_hotel.util.DateUtil;

public class App {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        try {
            ConnectionHandler.migrateDB();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println(e);
            return;
        }
        System.out.println("Welcome to restautant hotel!");
        int option = 0;

        do {
            System.out.println("Press the number of option you want:");
            System.out.println("1 - Add new menu item");
            System.out.println("2 - Get menu item");
            System.out.println("3 - Get menu item list admin");
            System.out.println("4 - Get menu item list customer");
            System.out.println("5 - Modify menu item");
            System.out.println("6 - Add new item to user cart");
            System.out.println("7 - Remove item from user cart");
            System.out.println("8 - Get user cart");
            System.out.println("9 - Exit");

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    App.addNewItem();
                    break;
                case 2:
                    App.getMenuItem();
                    break;
                case 3:
                    App.getMenuItemListAdmin();
                    break;
                case 4:
                    App.getMenuItemListCustomer();
                    break;
                case 5:
                    App.modifyMenuItem();
                    break;
                case 6:
                    App.addItemToUserCart();
                    break;
                case 7:
                    App.removeItemFromUserCart();
                    break;
                case 8:
                    App.getUserCart();
                    break;
                case 9:
                    break;
                default:
                    System.out.println("Option not recognized!");
                    break;
            }

        } while (option != 9);
        System.out.println("Goodbye!");
        scanner.close();
        try {
            ConnectionHandler.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public static void addNewItem() {
        System.out.println(
                "Enter the item in the following format: id,name,price,active(false or true),dateOfLaunch(dd/MM/yyyy),category, freeDelivery(false or true)");
        String line = scanner.nextLine();
        String[] values = line.split(",");
        if (values.length == 7) {
            MenuItem menuItem = new MenuItem(Long.parseLong(values[0]), values[1], Float.parseFloat(values[2]),
                    Boolean.parseBoolean(values[3]), DateUtil.convertToDate(values[4]), values[5],
                    Boolean.parseBoolean(values[6]));
            MenuItemDao menuItemDao = new MenuItemDaoSql();
            menuItemDao.addMenuItem(menuItem);
        } else {
            System.out.println("Wrong input");
        }
    }

    public static void getMenuItem() {
        System.out.println("Enter menu item id:");
        long id = scanner.nextLong();
        scanner.nextLine();
        MenuItemDao menuItemDao = new MenuItemDaoSql();
        System.out.println(menuItemDao.getMenuItem(id));
    }

    public static void getMenuItemListAdmin() {
        MenuItemDao menuItemDao = new MenuItemDaoSql();
        List<MenuItem> menuItemList = menuItemDao.getMenuItemListAdmin();
        for (MenuItem item : menuItemList) {
            System.out.println(item);
        }
    }

    public static void getMenuItemListCustomer() {
        MenuItemDao menuItemDao = new MenuItemDaoSql();
        List<MenuItem> menuItemList = menuItemDao.getMenuItemListCustomer();
        for (MenuItem item : menuItemList) {
            System.out.println(item);
        }
    }

    public static void modifyMenuItem() {
        System.out.println(
                "Enter the item in the following format: id,name,price,active(0 or 1),dateOfLaunch(dd/MM/yyyy),category, freeDelivery(0 or 1)");
        String line = scanner.nextLine();
        String[] values = line.split(",");
        if (values.length == 7) {
            MenuItem menuItem = new MenuItem(Long.parseLong(values[0]), values[1], Float.parseFloat(values[2]),
                    Boolean.parseBoolean(values[3]), DateUtil.convertToDate(values[4]), values[5],
                    Boolean.parseBoolean(values[6]));
            MenuItemDao menuItemDao = new MenuItemDaoSql();
            menuItemDao.modifyMenuItem(menuItem);
        } else {
            System.out.println("Wrong input");
        }
    }

    public static void addItemToUserCart() {
        System.out.println("Enter menu item id:");
        long menuItemId = scanner.nextLong();
        System.out.println("Enter user id:");
        long userId = scanner.nextLong();
        scanner.nextLine();
        CartDao cartDao = new CartDaoSql();
        cartDao.addCartItem(userId, menuItemId);
    }

    public static void removeItemFromUserCart() {
        System.out.println("Enter menu item id:");
        long menuItemId = scanner.nextLong();
        System.out.println("Enter user id:");
        long userId = scanner.nextLong();
        scanner.nextLine();
        CartDao cartDao = new CartDaoSql();
        cartDao.removeCartItem(userId, menuItemId);
    }

    public static void getUserCart() {
        System.out.println("Enter user id:");
        long userId = scanner.nextLong();
        scanner.nextLine();
        CartDao cartDao = new CartDaoSql();
        List<MenuItem> menuItemList;
        try {
            menuItemList = cartDao.getAllCartItems(userId);
            for (MenuItem item : menuItemList) {
                System.out.println(item);
            }
        } catch (CartEmptyException e) {
            e.printStackTrace();
            System.out.println("Empty cart");
        }
    }
}
