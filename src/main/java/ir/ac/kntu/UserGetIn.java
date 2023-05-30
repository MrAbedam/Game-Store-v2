package ir.ac.kntu;


import java.util.ArrayList;

import static ir.ac.kntu.Get.getString;
import static ir.ac.kntu.UserMainPage.*;

public class UserGetIn {


    public static void userLogin() {
        System.out.println("Enter username:");
        String newUserName = getString();
        System.out.println("Enter password:");
        String newPassword = getString();
        User testUser = findUser(newUserName);
        if (testUser == null) {
            System.out.println("No user matched try again.");
            StoreProgram.userChoices();
        } else if (!UserMainPage.checkUserPass(testUser, newPassword)) {
            System.out.println("Wrong password or username try again");
            StoreProgram.userChoices();
        } else {
            System.out.println("Logged in successfully!");
            UserLoggedInPage.showUserLoggedInMenu(testUser);
        }
    }

    public static void userSignUp() {
        String username;
        while (true) {
            System.out.println("Enter a username:");
            username = getString();
            if (usernameExists(username)) {
                System.out.println("Username already exists. Enter 'q' to quit, or any other key to try again.");
                String input = getString();
                if (input.equals("q")) {
                    System.out.println("Registration cancelled.");
                    StoreProgram.userChoices();
                    return;
                }
            } else {
                System.out.println("Username is available");
                break;
            }
        }
        String password;
        while (true) {
            System.out.println("Enter a password:");
            password = getString();
            if (!isPasswordValid(password)) {
                System.out.println("Password is not eligible. Enter 'q' to quit, or any other key to try again.");
                String input = getString();
                if (input.equals("q")) {
                    System.out.println("Registration cancelled.");
                    StoreProgram.userChoices();
                    return;
                }
            } else {
                System.out.println("Password is eligible.");
                break;
            }
        }
        System.out.println("Enter email:");
        String email = getString();
        System.out.println("Enter phone number:");
        String phoneNumber = getString();
        ArrayList<Game> gameList = new ArrayList<>();
        User newUser = new User(username, password, email, phoneNumber);
        allUsers.add(newUser);
        System.out.println("User added");
        UserLoggedInPage.showUserLoggedInMenu(newUser);
    }
}
