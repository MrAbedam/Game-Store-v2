package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Scanner;

import static ir.ac.kntu.StoreProgram.makeHashie;


public class AdminMainPage {

    static ArrayList<Admin> allAdmins = new ArrayList<>();

    public static void addAdmin(Admin admin){
        allAdmins.add(admin);
    }

    public static void displayAdminPage(Admin admin) {
        System.out.println("Welcome to admin main page "+ admin.getUsername());
        System.out.println("1.Games");
        System.out.println("2.Users");
        System.out.println("3.Return");
        makeHashie();
        Scanner sc = new Scanner(System.in);
        String ans = sc.nextLine();
        switch (ans) {
            case "1": {
                AdminGameList.adminGameListMenu(admin);
                break;
            }
            case "2": {
                AdminUserList.adminUserListMenu(admin);
                break;
            }
            case "3": {
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
}
