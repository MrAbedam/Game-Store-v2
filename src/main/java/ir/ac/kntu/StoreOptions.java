package ir.ac.kntu;

import java.util.ArrayList;

import static ir.ac.kntu.Colors.green;
import static ir.ac.kntu.Colors.reset;
import static ir.ac.kntu.Get.*;

public class StoreOptions {

    public static String storeMenuList() {
        System.out.println("Welcome to store.");
        System.out.println("1.Show all of items.");
        System.out.println("2.Search by name.");
        System.out.println("3.Search by price range.");
        System.out.println("4.Return.");
        String ans = getString();
        return ans;
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

    public static void searchForPriceCompressed(double minRange, double maxRange, User user){
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

    public static void storeMenu(User user) {
        String ans = storeMenuList();
        switch (ans) {
            case "1": {
                if (AdminGameList.listOfItems.isEmpty()) {
                    System.out.println("No Items found, try again");
                    storeMenu(user);
                } else {
                    Item chosenItem = chooseItem(AdminGameList.listOfItems, user);
                    chosenItem.showItemDetails(user);
                    storeMenu(user);
                }
                break;
            }
            case "2": {
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
                break;
            }
            case "3": {
                double minRange = getMinPrice();
                double maxRange = getMaxPrice();
                searchForPriceCompressed(minRange,maxRange,user);
                break;
            }
            case "4": {
                UserLoggedInPage.showUserLoggedInMenu(user);
                break;

            }
            default: {
                redirectToStoreMenu(user);
                break;
            }
        }
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
        for (Item testItem : AdminGameList.listOfItems) {
            if (testItem.getName().startsWith(searchName)) {
                filteredItemByName.add(testItem);
            }
        }
        return filteredItemByName;
    }


    public static ArrayList<Item> searchByPrice(double minPrice, double maxPrice) {
        System.out.println("Choose the item you want to view:");
        ArrayList<Item> filteredItemByPrice = new ArrayList<>();
        for (Item testGame : AdminGameList.listOfItems) {
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
            if (item instanceof Game){
                System.out.print(itemCounter+ "-Game."+ item.name + " => " + item.price + "$");
            }
            else if (item instanceof Monitor){
                System.out.print(itemCounter+ "*Device."+ item.name+" => "+ item.price +"$");
            }
            else if (item instanceof Controller){
                System.out.print(itemCounter+ "*Device."+ item.name+" => "+ item.price +"$");
            }
            if (user.doesUserOwn(item)) {
                System.out.print(green + " Owned." + reset);
            }
            itemCounter++;
            System.out.println(" ");
        }
    }
}
