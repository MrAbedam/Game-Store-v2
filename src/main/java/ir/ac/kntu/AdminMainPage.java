package ir.ac.kntu;

import java.util.Scanner;

import static ir.ac.kntu.StoreProgram.makeHashie;


public class AdminMainPage {


    public static void displayAdminPage() {
        System.out.println("1.Games");
        System.out.println("2.Users");
        System.out.println("3.Return");
        makeHashie();
        Scanner sc = new Scanner(System.in);
        String ans = sc.nextLine();
        switch (ans) {
            case "1": {
                AdminGameList.adminGameListMenu();
                break;
            }
            case "2": {
                AdminUserList.adminUserListMenu();
                break;
            }
            case "3": {
                System.out.println("Redirecting to main menu.");
                StoreProgram.displayMenu();
                break;
            }
            default: {
                System.out.println("Wrong input, redirecting to start of page.");
                displayAdminPage();
                break;
            }
        }
    }
}
