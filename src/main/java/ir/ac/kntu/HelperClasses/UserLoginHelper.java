package ir.ac.kntu.HelperClasses;

import ir.ac.kntu.UserPages.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class UserLoginHelper implements Serializable {

    public static ArrayList<User> allUsers = new ArrayList<>();

    public static boolean usernameExists(String username) {
        for (User user : allUsers) {
            if (user.getUserName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public static void addUser(User user) {
        allUsers.add(user);
    }

    public static User findUser(String userName) {
        for (User newUser : allUsers) {
            if (newUser.getUserName().equals(userName)) {
                return newUser;
            }
        }
        return null;
    }

    public static boolean checkUserPass(User user, String passWord) {
        if (user.getPassWord().equals(passWord)) {
            return true;
        }
        return false;
    }

    public static boolean isPasswordValid(String input) {
        // Check if String is more than 7 characters
        if (input.length() < 8) {
            return false;
        }

        // Check if String has both uppercase and lowercase letters
        if (!Pattern.compile("(?=.*[A-Z])(?=.*[a-z]).+").matcher(input).matches()) {
            return false;
        }

        // Check if String contains at least one digit
        if (!Pattern.compile(".*\\d.*").matcher(input).matches()) {
            return false;
        }

        // If all checks pass, return true
        return true;
    }
}
