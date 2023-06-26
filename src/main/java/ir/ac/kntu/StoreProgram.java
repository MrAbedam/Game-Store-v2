package ir.ac.kntu;


import ir.ac.kntu.AdminPages.Admin;
import ir.ac.kntu.AdminPages.AdminMainPage;
import ir.ac.kntu.Products.Controller;
import ir.ac.kntu.Products.Game;
import ir.ac.kntu.Products.Item;
import ir.ac.kntu.Products.Monitor;
import ir.ac.kntu.UserPages.User;
import ir.ac.kntu.UserPages.UserRegistrationPage;
import ir.ac.kntu.UserPages.UserLoginPage;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;


import static ir.ac.kntu.AdminPages.AdminGameList.listOfItems;
import static ir.ac.kntu.AdminPages.AdminMainPage.allAdmins;
import static ir.ac.kntu.HelperClasses.Get.getString;
import static ir.ac.kntu.HelperClasses.UserLoginHelper.allUsers;


public class StoreProgram {

    public static Instant loginTime;

    public static void makeHashie() {
        System.out.println("._._._._._.");
    }

    public static void displayMenu() {
        makeHashie();
        System.out.println("1.Admin");
        System.out.println("2.User");
        makeHashie();
        String ans = getString();
        switch (ans) {
            case "1": {
                adminLogIn();
                break;
            }
            case "2": {
                userChoices();
                break;
            }
            case "3": {
                loginTime = Instant.now();
                UserLoginPage.showUserLoggedInMenu(allUsers.get(0));
                break;
            }
            case "4": {
                loginTime = Instant.now();
                UserLoginPage.showUserLoggedInMenu(allUsers.get(1));
                break;
            }
            default: {
                System.out.println("Wrong input, redirecting to start of page.");
                displayMenu();
                break;
            }
        }
    }

    public static void userChoices() {
        System.out.println("1.Log in");
        System.out.println("2.Sign up");
        System.out.println("3.Return");
        String ans = getString();
        switch (ans) {
            case "1": {
                UserRegistrationPage.userLogin();
                break;
            }
            case "2": {
                UserRegistrationPage.userSignUp();
                break;
            }
            case "3": {
                displayMenu();
                break;
            }
            //cheat_code
            default: {
                System.out.println("Wrong input, redirecting to start of menu.");
                userChoices();
                break;
            }
        }
    }

    public static void updateDataStorage() {
        File file = new File("sss.txt");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (User testUser : allUsers) {
                String currentUserData = "Name: "+testUser.getUserName()+" password: "+testUser.getPassWord()+" number: "+testUser.getPhoneNumber()+" wallet: "+testUser.getWallet();
                currentUserData += " Games: ";
                for (Item testItem: testUser.getOwnedItems()){
                    currentUserData += testItem.getName()+"=> "+testItem.getPrice();
                }
                currentUserData += " Friends: ";
                for (User testFriend : testUser.getFriends()){
                    currentUserData += " "+testFriend.getUserName();
                }
                bufferedWriter.write(currentUserData+ "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void tryInputAgain() {
        System.out.println("Wrong Input!");
        System.out.println("1.Retry");
        System.out.println("2.Return");
    }

    private static void adminLogIn() {
        boolean wrongInput = true;
        System.out.println("Enter username:");
        String enteredUser = getString();
        for (Admin testAdmin : allAdmins) {
            if (testAdmin.getUsername().equals(enteredUser)) {
                System.out.println("Enter password");
                String enterdPass = getString();
                if (enterdPass.equals(testAdmin.getPassword())) {
                    AdminMainPage.displayAdminPage(testAdmin);
                } else {
                    System.out.println("Wrong password. redirecting to previous page");
                    displayMenu();
                }
                wrongInput = false;
            }
        }
        if (wrongInput) {
            System.out.println("No admins matched, redirecting to previous page");
            displayMenu();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        /*Admin admin = new Admin("aaa", "123");
        admin.addMainRole();
        Admin adminSell = new Admin("sell", "sell");
        adminSell.addSellerRole();
        Admin adminDev = new Admin("dev", "dev");
        adminDev.addDeveloperRole();
        Admin adminDev2 = new Admin("dev2", "dev2");
        adminDev2.addDeveloperRole();



        Game game1 = new Game("cod",
                "Call of Duty: Advanced Warfare is a 2014 first-person shooter video game published by Activision.",
                "Action fps-shooter",
                59.99, 2, true, admin);
        Game game2 = new Game("Dota2",
                "Dota 2 is a 2013 multiplayer online battle arena video game by Valve. The game is a sequel to Defense of the Ancients," +
                        "a community-created mod for Blizzard Entertainment's Warcraft III: Reign of Chaos.",
                "Strategy Moba",
                0.0, 1, false, adminDev);
        Controller device1 = new Controller("keyboard", "damn", 15, 1, "mmd", true);
        Controller device2 = new Controller("daste", "khar", 150, 2, "mmk", false);
        Monitor device3 = new Monitor("monitor", "khar", 150, 2, 50, 50, 50, 100);

        ArrayList<Game> user1Games = new ArrayList<>();
        ArrayList<Controller> user1Controllers = new ArrayList<>();
        ArrayList<Monitor> user1Monitors = new ArrayList<>();
        ArrayList<Item> user1Items = new ArrayList<>();
        User user1 = new User("mmd", "12345678Aa", "mmd@gmail.com", "09363340618");
        User user2 = new User("eli", "12345678Aa", "mmd@gmail.com", "09363340618");

        user1.setWallet(150);
        user2.setWallet(1000);
        allUsers.add(user1);
        allUsers.add(user2);
*/

        listOfItems = DaoWriter.readItem();
        allAdmins = DaoWriter.readAdmin();
        allUsers = DaoWriter.readUsers();
        displayMenu();
    }
}