package ir.ac.kntu.UserPages;

import static ir.ac.kntu.HelperClasses.Get.getDouble;
import static ir.ac.kntu.HelperClasses.Get.getString;

public class ProfileOption {
    public static void profileChoices(User user) {
        System.out.println("Enter an option:");
        System.out.println("1.Show profile.");
        System.out.println("2.Change a detail.");
        System.out.println("3.Charge your wallet.");
        System.out.println("4.Return.");
        String ans = getString();
        switch (ans) {
            case "1": {
                user.showDetails();
                profileChoices(user);
                break;
            }
            case "2": {
                user.changeUserDetails();
                break;
            }
            case "3": {
                System.out.println("Current amount: " + user.getWallet());
                System.out.println("Enter the amount:");
                double amount = getDouble();
                user.setWallet(user.getWallet() + amount);
                System.out.println("Wallet charged, Current amount: " + user.getWallet() + "$");
                profileChoices(user);
                break;
            }
            case "4": {
                UserLoginPage.showUserLoggedInMenu(user);
                break;
            }
            default: {
                System.out.println("Wrong input redirecting to start of page.");
                profileChoices(user);
                break;
            }
        }
    }
}
