package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Scanner;

import static ir.ac.kntu.StoreProgram.makeHashie;


public class AdminMainPage {

    static ArrayList<Admin> allDevs = new ArrayList<>();

    static ArrayList<Admin> allSellers = new ArrayList<>();

    static ArrayList<Admin> allAdmins = new ArrayList<>();


    public static void addAdmin(Admin admin){
        allAdmins.add(admin);
    }

    public static void checkDevRole(Admin admin){
        if (admin.isDeveloper()){
            AdminGameList.adminGameListMenu(admin);
        }else{
            System.out.println("Sorry you do not have the Game_Developer role.");
            displayAdminPage(admin);
        }
    }

    public static void checkSellerRole(Admin admin){
        if (admin.isSeller()){
            AdminDeviceList.adminDeviceListMenu(admin);
        }else{
            System.out.println("Sorry you do not have the Device_Seller role.");
            displayAdminPage(admin);
        }
    }

    public static void checkMainRole(Admin admin){
        if (admin.isMainAdmin()){
            AdminUserList.adminUserListMenu(admin);
        }else{
            System.out.println("Sorry you do not have the Main_Admin role.");
            displayAdminPage(admin);
        }
    }



    public static void displayAdminPage(Admin admin) {
        System.out.println("Welcome to admin main page "+ admin.getUsername() + admin.getRoles());
        System.out.println("1.Games");
        System.out.println("2.Users");
        System.out.println("3.Accessories");
        System.out.println("4.Return");
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
            case "3":{
                checkSellerRole(admin);
            }
            case "4": {
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
