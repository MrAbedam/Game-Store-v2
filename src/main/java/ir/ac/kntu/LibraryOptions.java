package ir.ac.kntu;

import java.util.ArrayList;

import static ir.ac.kntu.Colors.green;
import static ir.ac.kntu.Colors.reset;
import static ir.ac.kntu.Get.*;

public class LibraryOptions {



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

    public static String libraryMenuList() {
        System.out.println("Welcome to Library.");
        System.out.println("1.Show all of games.");
        System.out.println("2.Search by name.");
        System.out.println("3.Search by price range.");
        System.out.println("4.Return.");
        String ans = getString();
        return ans;
    }

    public static void communityOpt(Item item, User user){
        System.out.println("Enter choice:");
        System.out.println("1.Add a review.");
        System.out.println("2.See all reviews.");
        System.out.println("3.Return");
        String ans = getString();
        switch (ans){
            case "1":{
                System.out.println("Enter your review of "+item.getName());
                String review = getString();
                item.addReview(review,user);
                System.out.println("Press anything to go back.");
                getString();
                communityOpt(item,user);
                break;
            }
            case "2":{
                item.showReviews();
                System.out.println("Press anything to go back.");
                getString();
                communityOpt(item,user);
                break;
            }
            case "3":{
                gameCommunityAndRate(item,user);
                break;
            }
            default:{
                System.out.println("Wrong input, redirecting to start of page.");
                communityOpt(item,user);
                break;
            }
        }
    }

    public static void rateOpt(Item item, User user){
        System.out.println(item.getName() +"'s current rate: "+ item.getAvgRate());
        System.out.println("Enter rate:");
        double newRate = getDouble();
        while(newRate < 0 || newRate>10){
            System.out.println("Enter a number between 0 and 10!");
            newRate =getDouble();
        }
        item.addRate(user,newRate);
        item.updateRate();
        System.out.println("new Rate: "+ item.getAvgRate());
    }

    public static void gameCommunityAndRate(Item item, User user){
        System.out.println("Enter choice:");
        System.out.println("1.Community");
        System.out.println("2.Rate");
        System.out.println("3.Return");
        String ans = getString();
        switch (ans){
            case "1":{
                communityOpt(item, user);
                break;
            }
            case "2":{
                rateOpt(item, user);
                break;
            }
            case "3":{
                libraryMenu(user);
                break;
            }
            default:{
                System.out.println("Wrong input, redirecting to start of page.");
                gameCommunityAndRate(item,user);
                break;
            }
        }
    }

    public static void libraryMenuAllGames(User user){
        if (user.ownedItems.isEmpty()) {
            System.out.println("No items matched, try again.");
            libraryMenu(user);
        } else {
            Item chosenGame = chooseItem(user.ownedItems, user);
            chosenGame.showLibraryItemDetails(user);
            libraryMenu(user);
        }
    }

    public static void libraryMenu(User user) {
        String ans = libraryMenuList();
        switch (ans) {
            case "1": {
                libraryMenuAllGames(user);
                break;
            }
            case "2": {
                System.out.println("Enter the starting name:");
                String name = getString();
                ArrayList<Item> foundByName = searchByName(name, user);
                if (foundByName.isEmpty()) {
                    System.out.println("No games matched, try again.");
                    libraryMenu(user);
                } else {
                    Item chosenItem = chooseItem(foundByName, user);
                    chosenItem.showLibraryItemDetails(user);
                    libraryMenu(user);
                }
                break;
            }
            case "3": {
                double minRange = getMinPrice();
                double maxRange = getMaxPrice();
                ArrayList<Item> foundByPrice = searchByPrice(minRange, maxRange, user);
                if (foundByPrice.isEmpty()) {
                    System.out.println("No games matched, try again.");
                    libraryMenu(user);
                } else {
                    Item chosenItem = chooseItem(foundByPrice, user);
                    chosenItem.showLibraryItemDetails(user);
                    libraryMenu(user);
                }
                break;
            }
            case "4": {
                UserLoggedInPage.showUserLoggedInMenu(user);
                break;

            }
            default: {
                redirectToLibraryMenu(user);
                break;
            }
        }
    }

    public static void redirectToLibraryMenu(User user) {
        System.out.println("Wrong input redirecting to start of page.");
        libraryMenu(user);
    }

    public static void showLibraryGames(ArrayList<Item> listOfGivenItems, User user) {
        int itemCounter = 1;
        System.out.println("Items:");
        for (Item item : listOfGivenItems) {
            System.out.print(itemCounter + ". " + item.name + " => " + item.price + "$");
            if (user.doesUserOwn(item)) {
                System.out.print(green + " Owned." + reset);
            }
            System.out.println(" ");
            itemCounter++;
        }
    }

    public static Item chooseItem(ArrayList<Item> finalItemList, User user) {
        showLibraryGames(finalItemList,user);
        int ans = getInt();
        while ((ans > finalItemList.size() || ans < 1)) {
            System.out.println("Wrong input, try again:");
            ans = getInt();
        }
        return (finalItemList.get(ans - 1));
    }

    public static ArrayList<Item> searchByName(String searchName, User user) {
        ArrayList<Item> filteredGameByName = new ArrayList<>();
        for (Item testGame : user.ownedItems) {
            if (testGame.getName().startsWith(searchName)) {
                filteredGameByName.add(testGame);
            }
        }
        return filteredGameByName;
    }


    public static ArrayList<Item> searchByPrice(double minPrice, double maxPrice, User user) {
        System.out.println("Choose the game you want to view:");
        ArrayList<Item> filteredGameByPrice = new ArrayList<>();
        for (Item testGame : user.ownedItems) {
            if (testGame.getPrice() >= minPrice && testGame.getPrice() <= maxPrice) {
                filteredGameByPrice.add(testGame);
            }
        }
        return filteredGameByPrice;
    }

}
