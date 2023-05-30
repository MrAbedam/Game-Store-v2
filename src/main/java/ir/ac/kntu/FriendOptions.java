package ir.ac.kntu;

import java.util.ArrayList;

import static ir.ac.kntu.Get.getInt;
import static ir.ac.kntu.Get.getString;
import static ir.ac.kntu.UserMainPage.allUsers;

public class FriendOptions {

    public static void showUserList(User user, ArrayList<User> userList) {
        System.out.println("Choose a friend to view their games.");
        user.showFriends(userList);
        int choice = getInt();
        while (choice <= 0 || choice > userList.size()) {
            System.out.println("Wrong input, try again.");
            choice = getInt();
        }
        userList.get(choice - 1).showGames();
    }




    public static void friendSearch(User user){
        System.out.println("Enter name to search:");
        String searchName = getString();
        ArrayList <User> filteredFriends = user.searchNameFriends(searchName);
        if (filteredFriends.isEmpty()) {
            System.out.println("No friends matched, press anything to go back.");
            getString();
            return;
        }
        showUserList(user, user.friends);
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
        user.showFriends(filtered);
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
        receivingUser.receivedRequests.remove(sendingUser);
        sendingUser.sentRequests.remove(receivingUser);
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
        if (user.friends.isEmpty()) {
            System.out.println("No friends!");
        } else {
            showUserList(user, user.friends);
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
            case "5": {
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
        if(user.receivedRequests.isEmpty()){
            System.out.println("No requests to view, Press anything to go back.");
            getString();
            return;
        }
        user.showRequests();
        int choice = getInt();

        while(choice< 1 || choice> user.receivedRequests.size()){
            System.out.println("Wrong input, try again");
            choice = getInt();
        }
        System.out.println(user.receivedRequests.get(choice-1).userName+ " has sent you a friend request:");
        User testUser = user.receivedRequests.get(choice-1);
        System.out.println("1.Accept / 2.Decline");
        int reqAns = getInt();
        while (reqAns != 1 && reqAns !=2){
            System.out.println("Wrong input, try again.");
            reqAns = getInt();
        }
        if(reqAns == 1){
            user.friends.add(testUser);
            testUser.friends.add(user);
        }
        removeUsersFromRequests(testUser,user);
        removeUsersFromRequests(user,testUser);
    }
}
