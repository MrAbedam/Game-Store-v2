package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import static ir.ac.kntu.Get.getInt;
import static ir.ac.kntu.Get.getString;
import static ir.ac.kntu.StoreProgram.makeHashie;
import static ir.ac.kntu.UserMainPage.allUsers;
import static java.lang.Math.max;
import static java.lang.Math.min;


public class AdminMainPage {


    static ArrayList<Admin> allDevs = new ArrayList<>();

    static ArrayList<Admin> allSellers = new ArrayList<>();

    static ArrayList<Admin> allAdmins = new ArrayList<>();

    public static void addAdmin(Admin admin) {
        allAdmins.add(admin);
    }

    public static void checkDevRole(Admin admin) {
        if (admin.isDeveloper()) {
            AdminGameList.adminGameListMenu(admin);
        } else {
            System.out.println("Sorry you do not have the Game_Developer role.");
            displayAdminPage(admin);
        }
    }

    public static void checkSellerRole(Admin admin) {
        if (admin.isSeller()) {
            AdminDeviceList.adminDeviceListMenu(admin);
        } else {
            System.out.println("Sorry you do not have the Device_Seller role.");
            displayAdminPage(admin);
        }
    }

    public static void checkMainRole(Admin admin) {
        if (admin.isMainAdmin()) {
            AdminUserList.adminUserListMenu(admin);
        } else {
            System.out.println("Sorry you do not have the Main_Admin role.");
            displayAdminPage(admin);
        }
    }


    public static void displayAdminPage(Admin admin) {
        System.out.println("Welcome to admin main page " + admin.getUsername() + admin.getRoles());
        System.out.println("1.Games");
        System.out.println("2.Users");
        System.out.println("3.Accessories");
        System.out.println("4.Profile");
        System.out.println("5.Most online users");
        System.out.println("6.Return");
        makeHashie();
        Scanner sc = new Scanner(System.in);
        String ans = sc.nextLine();
        switch (ans) {
            case "1": {
                checkDevRole(admin);
                break;
            }
            case "2": {
                checkMainRole(admin);
                break;
            }
            case "3": {
                checkSellerRole(admin);
                break;
            }
            case "4": {
                showAdminProfile(admin);
                break;
            }
            case "5": {
                showMostOnlineUsers(admin);
                break;
            }
            case "6": {
                System.out.println("Redirecting to main menu.");
                StoreProgram.displayMenu();
                break;
            }
            default: {
                System.out.println("Wrong input, redirecting to start of page.");
                displayAdminPage(admin);
                break;
            }
        }
    }


    public static boolean isValidAdminName(String newName) {
        for (Admin testAdmin : allAdmins) {
            if (testAdmin.getUsername().equals(newName)) {
                return false;
            }
        }
        return true;
    }


    public static void mainAdminProf(Admin admin) {
        System.out.println("1.Show profile");
        System.out.println("2.Change profile");
        System.out.println("3.Add role to other admins");
        System.out.println("4.Return");
        String ans = getString();
        switch (ans) {
            case "1": {
                admin.showProfile();
                mainAdminProf(admin);
                break;
            }
            case "2": {
                admin.changeAdminDetails();
                mainAdminProf(admin);
            }
            case "3": {
                changeAdminRoles(admin);
                break;
            }
            case "4": {
                displayAdminPage(admin);
                break;
            }
            default: {
                System.out.println("Wrong input redirecting to start of page.");
                mainAdminProf(admin);
            }
        }
    }

    public static void otherAdminProf(Admin admin) {
        System.out.println("1.Show profile");
        System.out.println("2.Change profile");
        System.out.println("3.Return");
        String ans = getString();
        switch (ans) {
            case "1": {
                admin.showProfile();
                otherAdminProf(admin);
                break;
            }
            case "2": {
                admin.changeAdminDetails();
                otherAdminProf(admin);
            }
            case "3": {
                displayAdminPage(admin);
                break;
            }
            default: {
                System.out.println("Wrong input redirecting to start of page.");
                otherAdminProf(admin);
            }
        }
    }

    public static void showAdminProfile(Admin admin) {
        if (admin.isMainAdmin()) {
            mainAdminProf(admin);
        } else {
            otherAdminProf(admin);
        }
    }

    public static ArrayList<Admin> findAdminsBasedOnName(String name) {
        int adminCounter = 1;
        ArrayList<Admin> filteredList = new ArrayList<>();
        for (Admin testAdmin : allAdmins) {
            if (testAdmin.getUsername().startsWith(name)) {
                filteredList.add(testAdmin);
                System.out.println(adminCounter + " " + testAdmin.getUsername());
            }
        }
        if (filteredList.isEmpty()) {
            return null;
        }
        return filteredList;
    }

    public static void changeAdminRoles(Admin admin) {
        System.out.println("Enter admin name:");
        String name = getString();
        ArrayList<Admin> filteredAdmins = findAdminsBasedOnName(name);
        if (filteredAdmins == null) {
            System.out.println("No admins matched.");
            mainAdminProf(admin);
            return;
        }
        System.out.println("Choose an admin:");
        int choice = getInt();
        Admin curAdmin = filteredAdmins.get(choice - 1);
        System.out.println("Which role do you want to add: 1.Main 2.Developer 3.Seller");
        String roleNum = getString();
        switch (roleNum) {
            case "1": {
                curAdmin.addMainRole();
                break;
            }
            case "2": {
                curAdmin.addDeveloperRole();
                break;
            }
            case "3": {
                curAdmin.addSellerRole();
                break;
            }
            default: {
                break;
            }
        }
        mainAdminProf(admin);
    }

    public static void showMostOnlineUsers(Admin admin) {
        if (!admin.isMainAdmin()) {
            System.out.println("Sorry you need the Main_Role to access this section");
            displayAdminPage(admin);
            return;
        }
        ArrayList<User> topUsers = new ArrayList<>(allUsers);
        Collections.sort(topUsers);
        for (int cnt = 0; cnt < min(3, topUsers.size()); cnt++) {
            if (topUsers.get(cnt) != null) {
                System.out.println(topUsers.get(cnt).getUserName() + " => " + topUsers.get(cnt).getXp());
            }
        }
        displayAdminPage(admin);
    }
}
