package ir.ac.kntu.AdminPages;

import ir.ac.kntu.DaoWriter;
import ir.ac.kntu.UserPages.User;
import ir.ac.kntu.HelperClasses.UserHelperClass;
import ir.ac.kntu.HelperClasses.UserLoginHelper;

import java.io.Serializable;
import java.util.ArrayList;

import static ir.ac.kntu.HelperClasses.Get.getInt;
import static ir.ac.kntu.HelperClasses.Get.getString;
import static ir.ac.kntu.HelperClasses.UserLoginHelper.*;

public class AdminUserList implements Serializable {
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



    public static User findUserBasedOn(Admin admin) {
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
            default: {
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

    public static String adminUserListMenuOptions() {
        System.out.println("1.Show a user's details.");
        System.out.println("2.Change a user's details.");
        System.out.println("3.Add a user.");
        System.out.println("4.Remove a user.");
        System.out.println("5.Return");
        String ans = getString();
        return ans;
    }

    public static void adminUserRemove(User user, Admin admin) {
        allUsers.remove(user);
        System.out.println("User removed.");
        adminUserListMenu(admin);
    }


    public static void adminUserOptionSwitch(String ans, Admin admin) {
        User user;
        switch (ans) {
            case "1","2","4": {
                user = findUserBasedOn(admin);
                if (user == null) {
                    adminUserListMenu(admin);
                    break;
                } else if (ans.equals("1")) {
                    user.showDetails();
                    adminUserListMenu(admin);
                } else if (ans.equals("2")){
                    UserHelperClass.changeUserDetailsAsAdmin(user, admin);
                } else if (ans.equals("4")){
                    adminUserRemove(user, admin);
                }
                break;
            }
            case "3": {
                addUser(admin);
                adminUserListMenu(admin);
                break;
            }
            case "5": {
                DaoWriter.writeAndReadAllLists();
                AdminMainPage.displayAdminPage(admin);
                break;
            }
            default: {
                System.out.println("Wrong input, redirecting to start of page");
                adminUserListMenu(admin);
                break;
            }
        }
    }

    public static void adminUserListMenu(Admin admin) {
        String ans = adminUserListMenuOptions();
        adminUserOptionSwitch(ans, admin);
    }

    public static void addUser(Admin admin) {
        String username;
        while (true) {
            System.out.println("Enter a username:");
            username = getString();
            if (usernameExists(username)) {
                System.out.println("Username already exists. Enter 'q' to quit, or any other key to try again.");
                String input = getString();
                if (input.equals("q")) {
                    System.out.println("Adding user canceled.");
                    adminUserListMenu(admin);
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
                    adminUserListMenu(admin);
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
        adminUserListMenu(admin);

    }

    public static ArrayList<User> searchUserByName(String searchName) {
        ArrayList<User> filteredUserByName = new ArrayList<>();
        for (User testUser : UserLoginHelper.allUsers) {
            if (testUser.getUserName().startsWith(searchName)) {
                filteredUserByName.add(testUser);
            }
        }
        return filteredUserByName;
    }

    public static ArrayList<User> searchUserByEmail(String searchEmail) {
        ArrayList<User> filteredUserByEmail = new ArrayList<>();
        for (User testUser : UserLoginHelper.allUsers) {
            if (testUser.getEmail().startsWith(searchEmail)) {
                filteredUserByEmail.add(testUser);
            }
        }
        return filteredUserByEmail;
    }

    public static ArrayList<User> searchUserByPhoneNum(String searchPhoneNum) {
        ArrayList<User> filteredUserByPhoneNum = new ArrayList<>();
        for (User testUser : UserLoginHelper.allUsers) {
            if (testUser.getEmail().startsWith(searchPhoneNum)) {
                filteredUserByPhoneNum.add(testUser);
            }
        }
        return filteredUserByPhoneNum;
    }

}
