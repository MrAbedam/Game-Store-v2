package ir.ac.kntu;

import java.util.ArrayList;

import static ir.ac.kntu.Get.getInt;
import static ir.ac.kntu.Get.getString;
import static ir.ac.kntu.UserMainPage.*;

public class AdminUserList {
    public static void findUserBasedOnOptions() {
        System.out.println("Enter choice:");
        System.out.println("1.Search by name");
        System.out.println("2.Search by phone number");
        System.out.println("3.Search by email");
        System.out.println("4.Return");
    }

    public static void pressAnyThingError() {
        System.out.println("No Users matched with the name, press anything to return to previous menu.");
        getString();

    }

    public static User findUserBasedOn() {
        findUserBasedOnOptions();
        String ans = getString();
        switch (ans) {
            case "1": {
                System.out.println("Enter User's name:");
                String nameFilter = getString();
                ArrayList<User> finalUserList = searchUserByName(nameFilter);
                if (finalUserList.isEmpty()) {
                    pressAnyThingError();
                    return null;
                } else {
                    return (chooseUser(finalUserList));
                }
            }
            case "2": {
                System.out.println("Enter User's phone number:");
                String nameFilter = getString();
                ArrayList<User> finalUserList = searchUserByPhoneNum(nameFilter);
                if (finalUserList.isEmpty()) {
                    pressAnyThingError();
                    return null;
                } else {
                    return (chooseUser(finalUserList));
                }
            }
            case "3": {
                System.out.println("Enter User's email:");
                String nameFilter = getString();
                ArrayList<User> finalUserList = searchUserByEmail(nameFilter);
                if (finalUserList.isEmpty()) {
                    pressAnyThingError();
                    return null;
                } else {
                    return (chooseUser(finalUserList));
                }
            }
            case "4": {
                break;
            }
            default: {
                System.out.println("Wrong input, redirecting to previous page.");
                adminUserListMenu();
                break;
            }
        }
        return null;
    }

    public static void showGivenListOfUsers(ArrayList<User> listOfGivenUsers) {
        int userCounter = 1;
        for (User user : listOfGivenUsers) {
            System.out.println(userCounter + ". " + user.getUserName());
            userCounter++;
        }
    }

    public static User chooseUser(ArrayList<User> userList) {
        if (userList.isEmpty()) {
            return null;
        }
        System.out.println("Choose user from the list below:");
        showGivenListOfUsers(userList);
        int ans = getInt();
        while ((ans > userList.size() || ans < 1)) {
            System.out.println("Wrong input, try again:");
            ans = getInt();
        }
        return (userList.get(ans - 1));
    }

    public static String adminUserListMenuOptions(){
        System.out.println("1.Show a user's details.");
        System.out.println("2.Change a user's details.");
        System.out.println("3.Add a user.");
        System.out.println("4.Remove a user.");
        System.out.println("5.Return");
        String ans = getString();
        return ans;
    }

    public static void adminUserRemove(User user){
        allUsers.remove(user);
        System.out.println("User removed.");
        adminUserListMenu();
    }

    public static void adminUserOptionSwitch(String ans){
        User user;
        switch (ans) {
            case "1": {
                user = findUserBasedOn();
                if (user == null) {
                    adminUserListMenu();
                    break;
                } else {
                    user.showDetails();
                    adminUserListMenu();
                }
                break;
            }
            case "2": {
                user = findUserBasedOn();
                if (user == null) {
                    adminUserListMenu();
                    break;
                } else {
                    user.changeUserDetailsAsAdmin();
                }
                break;
            }
            case "3": {
                addUser();
                adminUserListMenu();
                break;
            }
            case "4": {
                user = findUserBasedOn();
                if (user == null) {
                    adminUserListMenu();
                    break;
                } else {
                    adminUserRemove(user);
                }
                break;
            }
            case "5": {
                AdminMainPage.displayAdminPage();
                break;
            }
            default: {
                System.out.println("Wrong input, redirecting to start of page");
                adminUserListMenu();
                break;
            }
        }
    }

    public static void adminUserListMenu() {
        String ans = adminUserListMenuOptions();
        adminUserOptionSwitch(ans);
    }

    public static void addUser() {
        String username;
        while (true) {
            System.out.println("Enter a username:");
            username = getString();
            if (usernameExists(username)) {
                System.out.println("Username already exists. Enter 'q' to quit, or any other key to try again.");
                String input = getString();
                if (input.equals("q")) {
                    System.out.println("Adding user canceled.");
                    adminUserListMenu();
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
                    System.out.println("Adding user canceled.");
                    adminUserListMenu();
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
        User newUser = new User(username, password, email, phoneNumber);
        allUsers.add(newUser);
        System.out.println("User added");
        adminUserListMenu();

    }

    public static ArrayList<User> searchUserByName(String searchName) {
        ArrayList<User> filteredUserByName = new ArrayList<>();
        for (User testUser : UserMainPage.allUsers) {
            if (testUser.getUserName().startsWith(searchName)) {
                filteredUserByName.add(testUser);
            }
        }
        return filteredUserByName;
    }

    public static ArrayList<User> searchUserByEmail(String searchEmail) {
        ArrayList<User> filteredUserByEmail = new ArrayList<>();
        for (User testUser : UserMainPage.allUsers) {
            if (testUser.getEmail().startsWith(searchEmail)) {
                filteredUserByEmail.add(testUser);
            }
        }
        return filteredUserByEmail;
    }

    public static ArrayList<User> searchUserByPhoneNum(String searchPhoneNum) {
        ArrayList<User> filteredUserByPhoneNum = new ArrayList<>();
        for (User testUser : UserMainPage.allUsers) {
            if (testUser.getEmail().startsWith(searchPhoneNum)) {
                filteredUserByPhoneNum.add(testUser);
            }
        }
        return filteredUserByPhoneNum;
    }

}
