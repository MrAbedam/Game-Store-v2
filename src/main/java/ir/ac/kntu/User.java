package ir.ac.kntu;

import java.util.ArrayList;

import static ir.ac.kntu.FriendOptions.removeUsersFromRequests;
import static ir.ac.kntu.Get.getString;
import static ir.ac.kntu.StoreProgram.makeHashie;
import static ir.ac.kntu.UserMainPage.isPasswordValid;
import static ir.ac.kntu.UserMainPage.usernameExists;

public class User {
    private String userName;

    private String passWord;

    private String email;

    private int xp;

    private double level;

    public double getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public User(String userName, String passWord, String email, String phoneNumber) {
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.wallet = 0;
        this.level = 1;
        this.xp = 1;
    }



    private String phoneNumber;

    private ArrayList<Item> ownedItems = new ArrayList<>();

    private ArrayList<Game> ownedGames = new ArrayList<>();

    private ArrayList<Monitor> ownedMonitors = new ArrayList<>();

    private ArrayList<Controller> ownedControllers = new ArrayList<>();

    private ArrayList<User> friends = new ArrayList<>();

    private ArrayList<User> sentRequests = new ArrayList<>();

    private ArrayList<User> receivedRequests = new ArrayList<>();


    public ArrayList<Item> getOwnedItems() {
        return ownedItems;
    }

    public void setOwnedItems(ArrayList<Item> ownedItems) {
        this.ownedItems = ownedItems;
    }

    public void setOwnedGames(ArrayList<Game> ownedGames) {
        this.ownedGames = ownedGames;
    }

    public ArrayList<Monitor> getOwnedMonitors() {
        return ownedMonitors;
    }

    public void setOwnedMonitors(ArrayList<Monitor> ownedMonitors) {
        this.ownedMonitors = ownedMonitors;
    }

    public ArrayList<Controller> getOwnedControllers() {
        return ownedControllers;
    }

    public void setOwnedControllers(ArrayList<Controller> ownedControllers) {
        this.ownedControllers = ownedControllers;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public void setSentRequests(ArrayList<User> sentRequests) {
        this.sentRequests = sentRequests;
    }

    public void setReceivedRequests(ArrayList<User> receivedRequests) {
        this.receivedRequests = receivedRequests;
    }

    double wallet;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    public void chargeWallet(double amount) {
        setWallet(amount + getWallet());
    }

    public String getPassWord() {
        return passWord;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ArrayList<Game> getOwnedGames() {
        return ownedGames;
    }

    public double getWallet() {
        return wallet;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public ArrayList<User> getSentRequests() {
        return sentRequests;
    }

    public ArrayList<User> getReceivedRequests() {
        return receivedRequests;
    }

    public void showDetails() {
        int gameCounter = 1;
        System.out.println("Username: " + getUserName());
        System.out.println("Password: " + getPassWord());
        System.out.println("Email: " + getEmail());
        System.out.println("Wallet: " + getWallet() + "$");
        System.out.println("Phone number: " + getPhoneNumber());
        System.out.println(Colors.yellow + "Level: "+ (int)this.getLevel()+Colors.reset);
        StoreOptions.showStoreGames(ownedItems, this);
        System.out.println("Press anything to go back.");
        getString();
    }

    public boolean doesUserOwn(Item item) {
        if (item instanceof Game) {
            for (Game testGame : ownedGames) {
                if (testGame == item) {
                    return true;
                }
            }
        }
        if (item instanceof Monitor) {
            for (Monitor testMonitor : ownedMonitors) {
                if (testMonitor == item) {
                    return true;
                }
            }
        }
        if (item instanceof Controller) {
            for (Controller testController : ownedControllers) {
                if (testController == item) {
                    return true;
                }
            }
        }
        return false;
    }

    public double calculateDiscountLevel(){
        switch ((int) this.getLevel()){
            case 1:{
                return 1;
            }
            case 2:{
                return 0.9;
            }
            case 3:{
                return 0.8;
            }
            case 4:{
                return 0.7;
            }
        }
        return 1;
    }

    public boolean isLevelValid(Game game){
        return (this.getLevel()>=game.getLevel());
    }

    public boolean buyGame(Game game) {
        double discount = this.calculateDiscountLevel();
        if (this.getLevel()!=1){
        System.out.println("Your level is "+(int)this.getLevel()+", you must pay "+ game.getPrice()*discount+"$");
        }
        if (this.doesUserOwn(game)) {
            System.out.println("You already own this game!");
            return false;
        } else if(!isLevelValid(game)){
            System.out.println("Your level must be at least "+ game.getLevel()+" to buy this game!");
            return false;
        } else if (this.getWallet() >= game.getPrice()*discount) {
            this.setWallet(this.getWallet() - game.getPrice()*discount);
            System.out.println(game.getName() + " has been added to your games.");
            ownedGames.add(game);
            ownedItems.add(game);
            return true;
        } else {
            System.out.println("Not enough balance in your account.");
            return false;
        }
    }

    public boolean buyController(Controller controller) {
        double discount = this.calculateDiscountLevel();
        if (this.getLevel()!=1){
            System.out.println("Your level is "+(int)this.getLevel()+", you must pay "+ controller.getPrice()*discount+"$");
        }
        if (controller.getSupplyNumber() == 0) {
            System.out.println("Sorry, no more " + controller.getName() + " available.");
            return false;
        } else if (this.getWallet() >= controller.getPrice()*discount) {
            this.setWallet(this.getWallet() - controller.getPrice()*discount);
            System.out.println(controller.getName() + " has been added to your devices.");
            controller.setSupplyNumber(controller.getSupplyNumber() - 1);
            ownedControllers.add(controller);
            ownedItems.add(controller);
            return true;
        } else {
            System.out.println("Not enough balance in your account.");
            return false;
        }
    }

    public boolean buyMonitor(Monitor monitor) {
        double discount = this.calculateDiscountLevel();
        if (this.getLevel()!=1){
            System.out.println("Your level is "+(int)this.getLevel()+", you must pay "+ monitor.getPrice()*discount+"$");
        }
        if (this.doesUserOwn(monitor)) {
            System.out.println("You already own this monitor!");
            return false;
        } else if (monitor.getSupplyNumber() == 0) {
            System.out.println("Sorry, no more " + monitor.getName() + " available.");
            return false;
        } else if (this.getWallet() >= monitor.getPrice()*discount) {
            this.setWallet(this.getWallet() - monitor.getPrice()*discount);
            System.out.println(monitor.getName() + " has been added to your devices.");
            monitor.setSupplyNumber(monitor.getSupplyNumber() - 1);
            ownedMonitors.add(monitor);
            ownedItems.add(monitor);
            return true;
        } else {
            System.out.println("Not enough balance in your account.");
            return false;
        }
    }
    public void changeToValidUser() {
        String newUsername;
        System.out.println("Current username: " + this.getUserName());
        while (true) {
            System.out.println("Enter your new username:");
            newUsername = getString();
            if (usernameExists(newUsername)) {
                System.out.println("Username already exists. Enter 'q' to quit, or any other key to try again.");
                String input = getString();
                if (input.equals("q")) {
                    System.out.println("Username change cancelled.");
                    this.changeUserDetails();
                    break;
                }
            } else {
                this.setUserName(newUsername);
                System.out.println("Username changed.");
                this.changeUserDetails();
                break;
            }
        }
    }

    public void changeUserDetails() {
        UserHelperClass.changeUserDetailsAsAdminMenu();
        String detailNumber = getString();
        switch (detailNumber) {
            case "1": {
                changeToValidUser();
                break;
            }
            case "2": {
                insertPasswordChange();
                break;
            }
            case "3": {
                insertChangeEmail();
                break;
            }
            case "4": {
                insertChangePhone();
                break;
            }
            case "5": {
                ProfileOption.profileChoices(this);
                break;
            }
            default: {
                System.out.println("Wrong input, redirecting to start of page.");
                this.changeUserDetails();
                break;
            }
        }
    }

    public void insertPasswordChange(){
        String newPassword;
        while (true) {
            System.out.println("Enter your new password:");
            newPassword = getString();
            if (!isPasswordValid(newPassword)) {
                System.out.println("Password is not eligible. Enter 'q' to quit, or any other key to try again.");
                String input = getString();
                if (input.equals("q")) {
                    System.out.println("Password change cancelled.");
                    this.changeUserDetails();
                    return;
                }
            } else {
                this.setPassWord(newPassword);
                System.out.println("Password changed.");
                this.changeUserDetails();
                break;
            }
        }
    }

    public void insertChangePhone() {
        System.out.println("Current phone number: " + this.getPhoneNumber());
        System.out.println("Enter your new phone number:");
        String newPhoneNumber = getString();
        this.setPhoneNumber(newPhoneNumber);
        System.out.println("Phone number changed.");
        this.changeUserDetails();
    }



    public void insertChangeEmail() {
        System.out.println("Current phone email: " + this.getEmail());
        System.out.println("Enter your new email:");
        String newEmail = getString();
        System.out.println("Email changed.");
        this.setEmail(newEmail);
        this.changeUserDetails();
    }



    public boolean requestPending(User newUser) {
        for (User user : this.sentRequests) {
            if (user.getUserName().equals(newUser.userName)) {
                System.out.println("Request is already pending.");
                return true;
            }
        }
        return false;
    }

    public boolean areFriends(User newUser) {
        for (User user : this.friends) {
            if (user.getUserName().equals(newUser.userName)) {
                System.out.println(newUser.userName + " is already your friend.");
                return true;
            }
        }
        return false;
    }

    public void sendRequest(User newUser) {
        if (this.receivedRequests.contains(newUser)) {
            System.out.println("This user had already sent you a friend request.");
            System.out.println("You guys are now friends!");
            removeUsersFromRequests(newUser, this);
            removeUsersFromRequests(this, newUser);
            newUser.friends.add(this);
            this.friends.add(newUser);
            return;
        }
        if (!areFriends(newUser) && !requestPending(newUser)) {
            System.out.println("Request sent to " + newUser.getUserName());
            this.sentRequests.add(newUser);
            newUser.receivedRequests.add(this);
        }
    }


    public void showRequests() {
        int requests = 1;
        System.out.println("Choose a request to answer.");
        for (User testUser : receivedRequests) {
            System.out.println(requests + ". " + testUser.getUserName() + " has sent you a friend request.");
            requests++;
        }
    }
}