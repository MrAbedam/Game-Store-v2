package ir.ac.kntu;

import java.time.Instant;
import java.util.ArrayList;

import static ir.ac.kntu.Get.getInt;
import static ir.ac.kntu.Get.getString;
import static ir.ac.kntu.UserHelperClass.showFriends;
import static ir.ac.kntu.UserMainPage.allUsers;

public class FriendOptions {

    public static void showUserList(User user, ArrayList<User> userList) {
        System.out.println("Choose a friend to view their games.");
        showFriends(userList);
        int choice = getInt();
        while (choice <= 0 || choice > userList.size()) {
            System.out.println("Wrong input, try again.");
            choice = getInt();
        }
        UserHelperClass.showGames(userList.get(choice - 1));
    }




    public static void friendSearch(User user){
        System.out.println("Enter name to search:");
        String searchName = getString();
        ArrayList <User> filteredFriends = UserHelperClass.searchNameFriends(searchName,user);
        if (filteredFriends.isEmpty()) {
            System.out.println("No friends matched, press anything to go back.");
            getString();
            return;
        }
        showUserList(user, user.getFriends());
    }

    public static void friendListFindAndReq(User user){
        System.out.println("Enter name to search:");
        String searchName = getString();
        ArrayList<User> filtered = new ArrayList<>();
        for( User testUser : allUsers){
            if (testUser.getUserName().startsWith(searchName) && testUser!=user){
                filtered.add(testUser);
            }
        }
        if (filtered.isEmpty()){
            System.out.println("No users matched, press anything to go back.");
            getString();
            return;
        }
        System.out.println("Enter a user to send request to");
        int userCounter = 1;
        showFriends(filtered);
        int choice = getInt();
        while (choice <= 0 || choice > filtered.size()) {
            System.out.println("Wrong input, try again.");
            choice = getInt();
        }
        User subjectUser = filtered.get(choice-1);
        user.sendRequest(subjectUser);
        System.out.println("Press anything to return.");
        getString();
    }

    public static void removeUsersFromRequests(User sendingUser, User receivingUser){
        receivingUser.getReceivedRequests().remove(sendingUser);
        sendingUser.getSentRequests().remove(receivingUser);
    }

    public static String friendOptMenu() {
        System.out.println("Enter choice:");
        System.out.println("1.Show all of friends.");
        System.out.println("2.Search between friends.");
        System.out.println("3.Add a friend.");
        System.out.println("4.See received requests.");
        System.out.println("5.Return");
        String ans = getString();
        return ans;
    }

    public static void friendShowAllFriends(User user){
        if (user.getFriends().isEmpty()) {
            System.out.println("No friends!");
        } else {
            showUserList(user, user.getFriends());
        }
        System.out.println("Press anything to go back");
        getString();
    }

    public static void friendOpt(User user) {
        String ans = friendOptMenu();
        switch (ans) {
            case "1": {
                friendShowAllFriends(user);
                friendOpt(user);
                break;
            }
            case "2": {
                friendSearch(user);
                friendOpt(user);
                break;
            }
            case "3": {
                friendListFindAndReq(user);
                friendOpt(user);
                break;
            }
            case "4": {
                answerRequest(user);
                friendOpt(user);
                break;
            }
            case "5": {///kir
                Instant loginTime = Instant.now();
                UserLoggedInPage.showUserLoggedInMenu(user);
                break;
            }
            default: {
                System.out.println("Wrong input, redirecting to start of page.");
                friendOpt(user);
                break;
            }
        }
    }

    public static void answerRequest(User user){
        if(user.getReceivedRequests().isEmpty()){
            System.out.println("No requests to view, Press anything to go back.");
            getString();
            return;
        }
        user.showRequests();
        int choice = getInt();

        while(choice< 1 || choice> user.getReceivedRequests().size()){
            System.out.println("Wrong input, try again");
            choice = getInt();
        }
        System.out.println(user.getReceivedRequests().get(choice-1).getUserName()+ " has sent you a friend request:");
        User testUser = user.getReceivedRequests().get(choice-1);
        System.out.println("1.Accept / 2.Decline");
        int reqAns = getInt();
        while (reqAns != 1 && reqAns !=2){
            System.out.println("Wrong input, try again.");
            reqAns = getInt();
        }
        if(reqAns == 1){
            user.getFriends().add(testUser);
            testUser.getFriends().add(user);
        }
        removeUsersFromRequests(testUser,user);
        removeUsersFromRequests(user,testUser);
    }
}
