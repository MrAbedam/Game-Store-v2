package ir.ac.kntu;

import java.util.ArrayList;
import java.util.Comparator;

import static ir.ac.kntu.Get.getString;
import static ir.ac.kntu.StoreProgram.makeHashie;
import static ir.ac.kntu.UserMainPage.isPasswordValid;
import static ir.ac.kntu.UserMainPage.usernameExists;

public class UserHelperClass {


    public static void changeUserDetailsAsAdmin(User user, Admin admin) {
        changeUserDetailsAsAdminMenu();
        String detailNumber = getString();
        switch (detailNumber) {
            case "1": {
                changeToValidUserAdmin(user, admin);
                break;
            }
            case "2": {
                insertPasswordChangeAsAdmin(user, admin);
                break;
            }
            case "3": {
                insertChangeEmailAsAdmin(user, admin);
                break;
            }
            case "4": {
                insertChangePhoneAsAdmin(user, admin);
                break;
            }
            case "5": {
                AdminUserList.adminUserListMenu(admin);
                break;
            }
            default: {
                System.out.println("Wrong input, redirecting to start of page.");
                changeUserDetailsAsAdmin(user, admin);
                break;
            }
        }
        AdminUserList.adminUserListMenu(admin);
    }

    public static void insertPasswordChangeAsAdmin(User user, Admin admin) {
        String newPassword;
        while (true) {
            System.out.println("Enter your new password:");
            newPassword = getString();
            if (!isPasswordValid(newPassword)) {
                System.out.println("Password is not eligible. Enter 'q' to quit, or any other key to try again.");
                String input = getString();
                if (input.equals("q")) {
                    System.out.println("Password change cancelled.");
                    changeUserDetailsAsAdmin(user, admin);
                    return;
                }
            } else {
                user.setPassWord(newPassword);
                System.out.println("Password changed.");
                changeUserDetailsAsAdmin(user, admin);
                break;
            }
        }
    }

    public static void changeUserDetailsAsAdminMenu() {
        System.out.println("Which detail do you want to change?");
        System.out.println("1.Username");
        System.out.println("2.Password");
        System.out.println("3.Email");
        System.out.println("4.Phone number");
        System.out.println("5.Return");
        makeHashie();
    }

    public static void changeToValidUserAdmin(User user, Admin admin) {
        String newUsername;
        System.out.println("Current username: " + user.getUserName());
        while (true) {
            System.out.println("Enter your new username:");
            newUsername = getString();
            if (usernameExists(newUsername)) {
                System.out.println("Username already exists. Enter 'q' to quit, or any other key to try again.");
                String input = getString();
                if (input.equals("q")) {
                    System.out.println("Username change cancelled.");
                    changeUserDetailsAsAdmin(user, admin);
                    break;
                }
            } else {
                user.setUserName(newUsername);
                System.out.println("Username changed.");
                changeUserDetailsAsAdmin(user, admin);
                break;
            }
        }
    }

    public static void insertChangePhoneAsAdmin(User user, Admin admin) {
        System.out.println("Current phone number: " + user.getPhoneNumber());
        System.out.println("Enter your new phone number:");
        String newPhoneNumber = getString();
        user.setPhoneNumber(newPhoneNumber);
        System.out.println("Phone number changed.");
        changeUserDetailsAsAdmin(user, admin);
    }

    public static void insertChangeEmailAsAdmin(User user, Admin admin) {
        System.out.println("Current phone email: " + user.getEmail());
        System.out.println("Enter your new email:");
        String newEmail = getString();
        System.out.println("Email changed.");
        user.setEmail(newEmail);
        changeUserDetailsAsAdmin(user, admin);
    }


    public static void showFriends(ArrayList<User> friends) {
        int friendCounter = 1;
        for (User user : friends) {
            System.out.println(friendCounter + ". " + user.getUserName());
            friendCounter++;
        }
    }

    public static ArrayList<User> searchNameFriends(String searchName, User user) {
        ArrayList<User> filteredList = new ArrayList<>();
        for (User testUser : user.getFriends()) {
            if (testUser.getUserName().startsWith(searchName)) {
                filteredList.add(testUser);
            }
        }
        return (filteredList);
    }

    public static void showGames(User user) {
        int gameCounter = 1;
        for (Game game : user.getOwnedGames()) {
            System.out.println(gameCounter + ". " + game.getName());
            gameCounter++;
        }
    }
}
