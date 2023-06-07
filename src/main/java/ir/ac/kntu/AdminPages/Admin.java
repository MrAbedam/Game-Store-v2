package ir.ac.kntu.AdminPages;

import ir.ac.kntu.Products.Game;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static ir.ac.kntu.AdminPages.AdminGameList.*;
import static ir.ac.kntu.AdminPages.AdminMainPage.*;
import static ir.ac.kntu.HelperClasses.Get.getInt;
import static ir.ac.kntu.HelperClasses.Get.getString;

public class Admin implements Comparable<Admin>{
    private String roles = "";

    private String username;

    private String password;

    private ArrayList<String> inbox = new ArrayList<>();

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
        allAdmins.add(this);
    }

    private ArrayList<Game> scheduledEvents = new ArrayList<>();

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public void addDeveloperRole() {
        this.roles += " Developer ";
        if (!allDevs.contains(this)){
            allDevs.add(this);
        }
    }

    public void addGameToScheduledEvents(Game game){
        this.getScheduledEvents().add(game);
    }

    public void addSellerRole() {
        this.roles += " Seller ";
        allSellers.add(this);
        if (!allSellers.contains(this)){
            allSellers.add(this);
        }
    }

    public void addMainRole() {
        this.roles += "Seller Developer Main";
        allSellers.add(this);
        allDevs.add(this);
    }

    public void setInbox(ArrayList<String> inbox) {
        this.inbox = inbox;
    }

    public boolean isMainAdmin() {
        if (this.roles.contains("Main")) {
            return true;
        }
        return false;
    }

    public ArrayList<String> getInbox() {
        return inbox;
    }

    public void addMsgInbox(String msg) {
        this.inbox.add(msg);
    }

    public void handleScheduledEvents(){
        if (!this.isDeveloper() && !this.isMainAdmin()){
            System.out.println("Sorry you don't have access to this part.");
        }
        if(this.getScheduledEvents().isEmpty()){
            System.out.println("No scheduled events available.");
        }else {
            int gameCounter = 0;
            for (Game testGame : scheduledEvents){
                System.out.println(gameCounter+1+". "+testGame.getName());
                gameCounter++;
            }
            System.out.println("Choose a game to handle");
            int ans = getInt();
            Game currentGame = scheduledEvents.get(ans-1);
            System.out.println("1.Accept / 2.Decline");
            int choice = getInt();
            if (choice==1){
                System.out.println("Game fixed");
                this.scheduledEvents.remove(currentGame);
                currentGame.flipIsOutOfOrder();
            }else {
                sendGameToAnotherAdmin(currentGame);
            }
        }
    }

    public void sendGameToAnotherAdmin(Game game){
        this.getScheduledEvents().remove(game);
        int currentIndex = game.getDevelopers().indexOf(this);
        if (currentIndex == game.getDevelopers().size()-1){
            game.getDevelopers().get(0).addGameToScheduledEvents(game);
        }else{
            game.getDevelopers().get(currentIndex+1).addGameToScheduledEvents(game);
        }
    }

    public void clearInbox() {
        this.getInbox().clear();
    }

    public void changeAdminDetails() {
        System.out.println("1.Change name");
        System.out.println("2.Change password");
        System.out.println("3.Return");
        String ans = getString();
        switch (ans) {
            case "1": {
                System.out.println("Enter new name");
                String newName = getString();
                if (AdminMainPage.isValidAdminName(newName)) {
                    this.setUsername(newName);
                } else {
                    System.out.println("This name is already taken.");
                }
                break;
            }
            case "2": {
                System.out.println("Enter new pass");
                this.setPassword(getString());
                break;
            }
            default: {
                break;
            }
        }
    }

    public void showProfile() {
        System.out.println("Name : " + this.getUsername());
        System.out.println("Password : " + this.getPassword());
        System.out.println("Roles : " + this.getAllRoles());
        System.out.println("Enter anything to go back");
        getString();
    }

    public ArrayList<Game> getScheduledEvents() {
        return scheduledEvents;
    }

    public void setScheduledEvents(ArrayList<Game> scheduledEvents) {
        this.scheduledEvents = scheduledEvents;
    }

    public void removeDeveloperRole() {
        this.setRoles(this.getRoles().replaceAll("Developer", ""));
    }

    public void removeSellerRole() {
        this.setRoles(this.getRoles().replaceAll("Seller", ""));
    }

    public boolean isDeveloper() {
        return (this.getRoles().contains("Developer"));
    }

    public boolean isSeller() {
        return (this.getRoles().contains("Seller"));
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAllRoles() {
        String allRoles = "";
        if (this.isMainAdmin()) {
            allRoles += " Main ";
        } else {
            if (this.isSeller()) {
                allRoles += " Seller ";
            }
            if (this.isDeveloper()) {
                allRoles += " Developer ";
            }
        }
        return allRoles;
    }

    public int getScheduledEventsSize(){
        return this.getScheduledEvents().size();
    }

    @Override
    public int compareTo( Admin otherAdmin) {
        return (this.getScheduledEventsSize()-otherAdmin.getScheduledEventsSize());
    }
}
