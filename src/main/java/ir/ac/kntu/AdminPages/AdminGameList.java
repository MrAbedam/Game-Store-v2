package ir.ac.kntu.AdminPages;

import ir.ac.kntu.HelperClasses.Colors;
import ir.ac.kntu.Products.*;
import ir.ac.kntu.UserPages.User;
import ir.ac.kntu.HelperClasses.UserLoginHelper;

import java.util.ArrayList;
import java.util.Collections;

import static ir.ac.kntu.AdminPages.AdminMainPage.allDevs;
import static ir.ac.kntu.HelperClasses.Get.*;
import static ir.ac.kntu.StoreProgram.makeHashie;
import static java.lang.Math.max;

public class AdminGameList {


    public static ArrayList<Game> listOfGames = new ArrayList<>();

    public static ArrayList<Game> outOfOrderGames = new ArrayList<>();

    public static ArrayList<Controller> listOfControllers = new ArrayList<>();

    public static ArrayList<Monitor> listOfMonitors = new ArrayList<>();

    public static ArrayList<Item> listOfItems = new ArrayList<>();

    public static ArrayList<Device> listOfDevices = new ArrayList<>();

    public static ArrayList<Game> findGameByName(String gameName) {
        ArrayList<Game> filteredGames = new ArrayList<>();
        for (Game foundGame : listOfGames) {
            if (foundGame.getName().equals(gameName)) {
                filteredGames.add(foundGame);
            }
        }
        return filteredGames;
    }

    public static ArrayList<Device> findDeviceByName(String deviceName) {
        ArrayList<Device> filteredDevice = new ArrayList<>();
        for (Device foundDevice : listOfDevices) {
            if (foundDevice.getName().equals(deviceName)) {
                filteredDevice.add(foundDevice);
            }
        }
        return filteredDevice;
    }

    public AdminGameList(ArrayList<Game> listOfGames, ArrayList<Monitor> listOfMonitors, ArrayList<Controller> listOfControllers) {
        this.listOfGames = listOfGames;
        this.listOfControllers = listOfControllers;
        this.listOfMonitors = listOfMonitors;
    }


    public static void removeItem(Item item) {
        for (User testUser : UserLoginHelper.allUsers) {
            if (item instanceof Game) {
                listOfGames.remove(item);
                listOfItems.remove(item);
                for (Game testGame : testUser.getOwnedGames()) {
                    if (testGame == item) {
                        testUser.getOwnedGames().remove(item);
                    }
                }
            } else if (item instanceof Monitor) {
                listOfMonitors.remove(item);
                listOfItems.remove(item);
                listOfDevices.remove(item);
                for (Monitor testMonitor : testUser.getOwnedMonitors()) {
                    if (testMonitor == item) {
                        testUser.getOwnedMonitors().remove(item);
                    }
                }
            } else if (item instanceof Controller) {
                listOfControllers.remove(item);
                listOfItems.remove(item);
                listOfDevices.remove(item);
                for (Controller testController : testUser.getOwnedControllers()) {
                    if (testController == item) {
                        testUser.getOwnedControllers().remove(item);
                    }
                }
            }
        }
    }

    public static void adminGameChangeDetails(Admin admin) {
        System.out.println("Enter game's name.");
        String filterName = getString();
        ArrayList<Game> filteredList = findGameByName(filterName);
        if (filteredList.isEmpty()) {
            System.out.println("No Games Matched. Enter anything to return to Admins gameList.");
            String tmp = getString();
            makeHashie();
            adminGameListMenu(admin);
        } else {
            System.out.println("Choose a game between the filtered games:");
            showGivenListOfGames(filteredList);
            int gameChoice = getInt();
            while (gameChoice > filteredList.size() || gameChoice < 1) {
                System.out.println("Wrong input, try again:");
                gameChoice = getInt();
            }
            Game chosenGame = filteredList.get(gameChoice - 1);
            if (chosenGame.isPartOfTeam(admin)) {
                changeGameDetail(chosenGame, admin);
            } else {
                System.out.println("Sorry you are not a part of the production team for this game!");
            }
            adminGameListMenu(admin);
        }
    }


    public static void adminRemoveGame(Admin admin) {
        System.out.println("Enter game's name.");
        String filterName = getString();
        ArrayList<Game> filteredList = findGameByName(filterName);
        if (filteredList.isEmpty()) {
            System.out.println("No Games Matched. Enter anything to return to Admins gameList.");
            getString();
            adminGameListMenu(admin);
        } else {
            System.out.println("Choose a game between the filtered games:");
            showGivenListOfGames(filteredList);
            int gameChoice = getInt();
            Game chosenGame = filteredList.get((gameChoice - 1) % filteredList.size());
            if (chosenGame.isPartOfTeam(admin)) {
                removeItem(chosenGame);
            } else {
                System.out.println("Sorry you are not a part of the production team");
            }
            adminGameListMenu(admin);
        }
    }

    public static void inboxNotif(Admin admin) {
        if (admin.getInbox().isEmpty()) {
            System.out.println("5.Inbox (0)");
        } else {
            System.out.println(Colors.purple + "5.Inbox (" + admin.getInbox().size() + ")" + Colors.reset);
        }
    }

    public static void adminGameListMenuOptions(Admin admin) {
        System.out.println("Admins gameList page.");
        System.out.println("1.Add a game.");
        System.out.println("2.Change a game's details.");
        System.out.println("3.Remove a game.");
        System.out.println("4.Show all of games.");
        inboxNotif(admin);
        System.out.println("6.Out Of order games.");
        System.out.println("7.Summer sale");
        System.out.println("8.Return.");
    }

    public static void summerSale(Admin admin){
        if (!admin.isMainAdmin()){
            System.out.println("Sorry you need admin Main_Role to access this section");
        } else {
            System.out.println("Enter discount percentage (example: 15)");
            double ans = getDouble();
            for (Game testGame : listOfGames){
                testGame.setPrice(max((int) (testGame.getPrice()*(100-ans)/100),0));
            }
            System.out.println("Summer sale effected the prices!");
        }
        adminGameListMenu(admin);
    }

    public static void adminGameStatus(Admin admin){
        if (!admin.isMainAdmin()){
            System.out.println("You need Main_Role to access this section");
            adminGameListMenu(admin);
            return;
        }
        System.out.println("1.Flip games to working");
        System.out.println("2.Flip games to out Of order");
        System.out.println("3.Return");
        String ans = getString();
        switch (ans){
            case "1":{
                adminWorkingGames(admin);
                break;
            }
            case "2":{
                adminOutOfOrderGames(admin);
                break;
            }
            case "3":{
                adminGameListMenu(admin);
                break;
            }
            default:{
                adminGameStatus(admin);
                break;
            }
        }
    }

    public static void adminWorkingGames(Admin admin){
        int gameCounter = 0;
        if (outOfOrderGames.isEmpty()){
            System.out.println("No out of order games.");
            adminGameListMenu(admin);
            return;
        }else {
            for (Game testGame : outOfOrderGames){
                System.out.println((gameCounter+1)+". "+testGame.getName()+Colors.red+" => Out of order"+Colors.reset);
                gameCounter++;
            }
        }
        int ans = getInt();
        Game currentGame = outOfOrderGames.get(ans-1);
        currentGame.flipIsOutOfOrder();
        System.out.println("Status flipped!");
        adminGameListMenu(admin);
    }

    public static void adminOutOfOrderGames(Admin admin){
        System.out.println("Choose a game to flip working status");
        int gameCounter = 0;
        for (Game testGame : listOfGames){
            System.out.println((gameCounter+1)+". "+testGame.getName()+Colors.green+" => Working"+Colors.reset);
            gameCounter++;
        }
        int ans = getInt();
        Game currentGame = listOfGames.get(ans-1);
        currentGame.flipIsOutOfOrder();
        System.out.println("Status flipped!");
        notifyFreeDeveloper(currentGame);
        adminGameListMenu(admin);
    }

    public static void notifyFreeDeveloper(Game game){
        Collections.sort(game.getDevelopers());
        Admin freeAdmin = game.getDevelopers().get(0);
        freeAdmin.addGameToScheduledEvents(game);
    }

    public static void showInbox(Admin admin) {
        if (admin.getInbox().isEmpty()) {
            System.out.println("Your inbox is empty.");
        } else {
            for (String testString : admin.getInbox()) {
                System.out.println(testString);
            }
        }
        System.out.println("Enter anything to go back.");
        getString();
        admin.clearInbox();
        adminGameListMenu(admin);
    }

    public static void adminShowGames(Admin admin){
        showGivenListOfGames(listOfGames);
        System.out.println("Enter anything to return to Admins gameList.");
        String tmp = getString();
        makeHashie();
        adminGameListMenu(admin);
    }

    public static void adminGameListMenu(Admin admin) {
        adminGameListMenuOptions(admin);
        String ans = getString();
        switch (ans) {
            case "1": {
                makeGame(admin);
                adminGameListMenu(admin);
                break;
            }
            case "2": {
                adminGameChangeDetails(admin);
                break;
            }
            case "3": {
                adminRemoveGame(admin);
                break;
            }
            case "4": {
                adminShowGames(admin);
                break;
            }
            case "5": {
                showInbox(admin);
                break;
            }
            case "6":{
                adminGameStatus(admin);
                break;
            }
            case"7":{
                summerSale(admin);
                break;
            }
            case "8": {
                AdminMainPage.displayAdminPage(admin);
                break;
            }
            default: {
                System.out.println("Wrong input, redirecting to start of page.");
                adminGameListMenu(admin);
                break;
            }
        }
    }

    public static void changeGameDetailOptions() {
        System.out.println("Which detail do you want to change?");
        System.out.println("1.Name");
        System.out.println("2.Description");
        System.out.println("3.Genre");
        System.out.println("4.Price");
        System.out.println("5.Developer team");
        System.out.println("6.Return");
        makeHashie();
    }

    public static void chooseAndAddDeveloper(Game game) {
        int devCounter = 1;
        System.out.println("Choose A developer to add to your game.");
        for (Admin testAdmin : allDevs) {
            System.out.println(devCounter + ". " + testAdmin.getUsername());
            devCounter++;
        }
        int ans = (getInt() - 1) % devCounter;
        Admin newDev = allDevs.get(ans);
        if (game.isPartOfTeam(newDev)) {
            System.out.println(newDev.getUsername() + " was already one of the developers of this game");
        } else {
            System.out.println(newDev.getUsername() + " has been added to the developer team.");
            game.addDev(newDev);
        }
    }


    public static void changeGameDetailName(Game game) {
        System.out.println("Current name: " + game.getName());
        System.out.println("Enter new name:");
        String newName = getString();
        game.setName(newName);
        System.out.println("Name changed!");
        makeHashie();
    }

    public static void changeGameDescription(Game game){
        System.out.println("Current description: " + game.getDescription());
        System.out.println("Enter new description:");
        String newDescription = getString();
        game.setDescription(newDescription);
        System.out.println("Description changed!");
        makeHashie();
    }

    public static void changeGameGenre(Game game){
        System.out.println("Current genre: " + game.getGenre());
        System.out.println("Enter new genre:");
        String newGenre = getString();
        game.setGenre(newGenre);
        System.out.println("Genre changed!");
        makeHashie();
    }

    public static void changeGamePrice(Game game){
        System.out.println("Current price: " + game.getPrice());
        System.out.println("Enter new price:");
        double newPrice = getDouble();
        game.setPrice(newPrice);
        System.out.println("Price changed!");
        makeHashie();
    }

    public static void changeGameDetail(Game game, Admin admin) {
        changeGameDetailOptions();
        int detailNumber = getInt();
        switch (detailNumber) {
            case 1: {
                changeGameDetailName(game);
                changeGameDetail(game, admin);
                break;
            }
            case 2: {
                changeGameDescription(game);
                changeGameDetail(game, admin);
                break;
            }
            case 3: {
                changeGameGenre(game);
                changeGameDetail(game, admin);
                break;
            }
            case 4: {
                changeGamePrice(game);
                changeGameDetail(game, admin);
                break;
            }
            case 5: {
                chooseAndAddDeveloper(game);
                changeGameDetail(game, admin);
            }
            case 6: {
                adminGameListMenu(admin);
                break;
            }
            default: {
                System.out.println("Wrong input, redirecting to start of page.");
                changeGameDetail(game, admin);
                break;
            }
        }
    }

    public static void makeGame(Admin admin) {
        System.out.println("Enter game's name:");
        String gameName = getString();
        System.out.println("Enter game's description");
        String gameDescription = getString();
        System.out.println("Enter game's genre");
        String gameGenre = getString();
        System.out.println("Enter game's price");
        double gamePrice = getDouble();
        System.out.println("Enter game's level");
        int gameLevel = getInt();
        boolean isGameBeta;
        System.out.println("Is game in beta mode? Enter y for yes, or anything else for no");
        String ans = getString();
        if (ans.equals("y") || ans.equals("Y")) {
            isGameBeta = true;
        } else {
            isGameBeta = false;
        }
        Game newGame = new Game(gameName, gameDescription, gameGenre, gamePrice, gameLevel, isGameBeta,admin);
        System.out.println("Game added!");
    }

    public static void addItem(Item item) {
        if (item instanceof Game) {
            listOfGames.add((Game) item);
            return;
        }
        if (item instanceof Monitor) {
            listOfMonitors.add((Monitor) item);
            return;
        }
        if (item instanceof Controller) {
            listOfControllers.add((Controller) item);
            return;
        }
    }

    public void addMontior(Monitor monitor) {
        listOfMonitors.add(monitor);

    }

    public static void addGame(Game game) {
        listOfGames.add(game);
    }

    public void addController(Controller controller) {
        listOfControllers.add(controller);
    }


    /// change to list of devices =>
    public static void showGivenListOfGames(ArrayList<Game> listOfGivenGames) {
        int gameCounter = 1;
        for (Game game : listOfGivenGames) {
            System.out.println(gameCounter + ". " + game.getName() + " => " + game.getPrice() + "$ " + "Level:" + game.getLevel());
            gameCounter++;
        }
    }


}
