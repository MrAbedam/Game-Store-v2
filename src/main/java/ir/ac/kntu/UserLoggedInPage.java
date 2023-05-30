package ir.ac.kntu;

import static ir.ac.kntu.Get.getString;
import static ir.ac.kntu.StoreProgram.loginTime;

import java.time.Duration;
import java.time.Instant;


public class UserLoggedInPage {

    public static void userLoginMenu(User user){
        System.out.println("Welcome to your main page " + user.getUserName());
        System.out.println("Choose what you want to do:");
        System.out.println("1.Profile");
        System.out.println("2.Store");
        System.out.println("3.Library");
        System.out.println("4.Friends");
        System.out.println("5.Return");
    }

    public static void userLevelSet(User user,Instant login, Instant logout){
        Duration duration = Duration.between(login,logout);
        user.setXp((int) (user.getXp() + duration.toSeconds()));
        int currentXp = user.getXp();
        if (currentXp<20){
            user.setLevel(1);
        }else if(currentXp<50){
            user.setLevel(2);
        }else if(currentXp<100){
            user.setLevel(3);
        }else if(currentXp<200){
            user.setLevel(4);
        }

    }

    public static void showUserLoggedInMenu(User user) {
        userLoginMenu(user);
        String ans = getString();
        switch (ans) {
            case "1": {
                ProfileOption.profileChoices(user);
                break;
            }
            case "2": {
                StoreOptions.storeMenu(user);
                break;
            }
            case "3": {
                LibraryOptions.libraryMenu(user);
                break;
            }
            case "4": {
                FriendOptions.friendOpt(user);
                break;
            }
            case "5": {
                System.out.println("Redirecting to main menu.");
                userLevelSet(user,loginTime,Instant.now());
                StoreProgram.displayMenu();
                break;
            }
            default: {
                System.out.println("Wrong input, redirecting to start of page.");
                showUserLoggedInMenu(user);
                break;
            }
        }
    }
}
