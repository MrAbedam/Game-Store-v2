package ir.ac.kntu.UserPages;

import ir.ac.kntu.AdminPages.Admin;
import ir.ac.kntu.AdminPages.AdminGameList;
import ir.ac.kntu.DaoWriter;
import ir.ac.kntu.HelperClasses.Colors;
import ir.ac.kntu.Products.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;

import static ir.ac.kntu.AdminPages.AdminGameList.*;
import static ir.ac.kntu.HelperClasses.Colors.*;
import static ir.ac.kntu.HelperClasses.Get.*;
import static ir.ac.kntu.HelperClasses.UserLoginHelper.allUsers;
import static java.lang.Math.min;

public class StoreOptions implements Serializable {

    public static String storeMenuList() {
        System.out.println("Welcome to store.");
        System.out.println("1.Show all of items.");
        System.out.println("2.Search by name.");
        System.out.println("3.Search by price range.");
        System.out.println("4.Search by type.");
        System.out.println("5.Show favorite items");
        System.out.println("6.Calculate price item");
        System.out.println("7.Return.");
        String ans = getString();
        return ans;
    }

    public static void searchByType(User user) {
        System.out.println("1.Games");
        System.out.println("2.Devices");
        System.out.println("3.Monitors");
        System.out.println("4.Controllers");
        System.out.println("5.Return");
        String ans = getString();
        switch (ans) {
            case "1": {
                storeShowAllGivenProducts(user,"Game");
                break;
            }
            case "2": {
                storeShowAllGivenProducts(user,"Device");
                break;
            }
            case "3": {
                storeShowAllGivenProducts(user,"Monitor");
                break;
            }
            case "4": {
                storeShowAllGivenProducts(user,"Controller");
                break;
            }
            case "5": {
                storeMenu(user);
                break;
            }
            default: {
                System.out.println("Wrong input, redirecting to start of page.");
                searchByType(user);
            }
        }
    }


    public static double getMinPrice() {
        System.out.println("Enter the min price:");
        double minRange = getDouble();
        return minRange;
    }

    public static double getMaxPrice() {
        System.out.println("Enter the max price:");
        double maxRange = getDouble();
        return maxRange;
    }

    public static void searchForPriceCompressed(double minRange, double maxRange, User user) {
        ArrayList<Item> foundByPrice = searchByPrice(minRange, maxRange);
        if (foundByPrice.isEmpty()) {
            System.out.println("No games matched, try again.");
            storeMenu(user);
        } else {
            Item chosenItem = chooseItem(foundByPrice, user);
            chosenItem.showItemDetails(user);
            storeMenu(user);
        }
    }

    public static void storeShowAllGames(User user) {
        if (listOfItems.isEmpty()) {
            System.out.println("No Items found, try again");
            storeMenu(user);
        } else {
            Item chosenItem = chooseItem(listOfItems, user);
            chosenItem.showItemDetails(user);
            storeMenu(user);
        }
    }

    public static ArrayList<Item> findItemBasedOnName(String name, String type) {
        ArrayList<Item> filteredList = new ArrayList<Item>();
        ArrayList<Item> searchList = new ArrayList<>();
        switch (type) {
            case "Game": {
                searchList = new ArrayList<>(listOfGames);
                break;
            }
            case "Controller": {
                searchList = new ArrayList<>(listOfControllers);
                break;
            }
            case "Device": {
                searchList = new ArrayList<>(listOfDevices);
                break;
            }
            default:{
                searchList = new ArrayList<>(listOfMonitors);
                break;
            }
        }
        for (Item testItem : searchList) {
            if (testItem.getName().startsWith(name)) {
                filteredList.add(testItem);
            }
        }
        return filteredList;
    }

    public static void storeShowAllGivenProducts(User user, String type) {
        System.out.println("Enter name:");
        String name = getString();
        ArrayList<Item> givenListOfItems = findItemBasedOnName(name, type);
        if (givenListOfItems.isEmpty()) {
            System.out.println("Nothing matched, try again");
        } else {
            Item chosenItem = chooseItem(givenListOfItems, user);
            chosenItem.showItemDetails(user);
        }
        storeMenu(user);
    }

    public static void showStoreGamesBasedOnName(User user) {
        System.out.println("Enter the starting name:");
        String name = getString();
        ArrayList<Item> foundByName = searchItemByName(name);
        if (foundByName.isEmpty()) {
            System.out.println("No Items matched, try again.");
            storeMenu(user);
        } else {
            Item chosenItem = chooseItem(foundByName, user);
            chosenItem.showItemDetails(user);
            StoreOptions.storeMenu(user);
        }
    }

    public static void storeMenu(User user) {
        String ans = storeMenuList();
        switch (ans) {
            case "1": {
                storeShowAllGames(user);
                break;
            }
            case "2": {
                showStoreGamesBasedOnName(user);
                break;
            }
            case "3": {
                double minRange = getMinPrice();
                double maxRange = getMaxPrice();
                searchForPriceCompressed(minRange, maxRange, user);
                break;
            }
            case "4": {
                searchByType(user);
                break;
            }
            case "5": {
                showFavoriteItems(user);
                break;
            }
            case "6": {
                calculatePriceItem(user);
                break;
            }
            case "7": {
                DaoWriter.writeAndReadAllLists();
                Instant loginTime = Instant.now();
                UserLoginPage.showUserLoggedInMenu(user);
                break;
            }
            default: {
                redirectToStoreMenu(user);
                break;
            }
        }
    }

    public static void calculatePriceItem(User user) {
        double sum = 0;
        showStoreGames(listOfItems, user);
        System.out.println("Enter 0 to exit, enter item number to add to sum");
        int ans = getInt();
        while (ans != 0) {
            sum += listOfItems.get(ans - 1).getPrice();
            if (sum > user.getWallet()) {
                System.out.print(red);
            }
            System.out.println("Current sum: " + sum + reset);
            ans = getInt();
        }
        System.out.println("Sum of all the chosen games would be " + sum + "$");
        if (user.getLevel() > 1) {
            System.out.println(cyan + "Buy you only need to pay " + sum * user.calculateDiscountLevel() + "$" + reset);
        }
        storeMenu(user);
    }

    public static void showFavoriteItems(User user) {
        ArrayList<Item> topItems = new ArrayList<>(listOfItems);
        Collections.sort(topItems);
        for (int cnt = 0; cnt < min(3, topItems.size()); cnt++) {
            if (topItems.get(cnt) != null) {
                System.out.println(Colors.purple + (cnt + 1) + ". " + topItems.get(cnt).getName() + " => "
                        + topItems.get(cnt).getSoldNumber() + reset);
            }
        }
        storeMenu(user);
    }

    public static void redirectToStoreMenu(User user) {
        System.out.println("Wrong input redirecting to start of page.");
        storeMenu(user);
    }

    public static Item chooseItem(ArrayList<Item> finalGameList, User user) {
        showStoreGames(finalGameList, user);
        int ans = getInt();
        while ((ans > finalGameList.size() || ans < 1)) {
            System.out.println("Wrong input, try again:");
            ans = getInt();
        }
        return (finalGameList.get(ans - 1));
    }

    public static ArrayList<Item> searchItemByName(String searchName) {
        ArrayList<Item> filteredItemByName = new ArrayList<>();
        for (Item testItem : listOfItems) {
            if (testItem.getName().startsWith(searchName)) {
                filteredItemByName.add(testItem);
            }
        }
        return filteredItemByName;
    }


    public static ArrayList<Item> searchByPrice(double minPrice, double maxPrice) {
        System.out.println("Choose the item you want to view:");
        ArrayList<Item> filteredItemByPrice = new ArrayList<>();
        for (Item testGame : listOfItems) {
            if (testGame.getPrice() >= minPrice && testGame.getPrice() <= maxPrice) {
                filteredItemByPrice.add(testGame);
            }
        }
        return filteredItemByPrice;
    }

    public static void showStoreGames(ArrayList<Item> listOfGivenItems, User user) {
        int itemCounter = 1;
        System.out.println("Items:");
        for (Item item : listOfGivenItems) {
            if (item instanceof Game) {
                System.out.print(itemCounter + "| Game: " + item.getName() + " => " + item.getPrice() + "$ "
                        + "Level:" + ((Game) item).getLevel() + " Game rate: " + item.getAvgRate() + " |");
            } else if (item instanceof Monitor) {
                System.out.print(itemCounter + "* Monitor: " + item.getName() + " => " + item.getPrice() + "$ *");
            } else if (item instanceof Controller) {
                System.out.print(itemCounter + "* Controller: " + item.getName() + " => " + item.getPrice() + "$ *");
            }
            if (user.doesUserOwn(item)) {
                System.out.print(green + " Owned." + reset);
            }
            itemCounter++;
            System.out.println(" ");
        }
    }
}
