package ir.ac.kntu;

import java.util.ArrayList;

import static ir.ac.kntu.AdminMainPage.*;
import static ir.ac.kntu.Get.getString;

public class Admin {
    private String roles = "";

    private String username;

    private String password;

    private ArrayList<String> inbox = new ArrayList<>();

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
        allAdmins.add(this);
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public void addDeveloperRole() {
        this.roles += " Developer ";
        allDevs.add(this);
    }

    public void addSellerRole() {
        this.roles += " Seller ";
        allSellers.add(this);
    }

    public void addMainRole() {
        this.roles += "Seller Developer Main";
        allSellers.add(this);
        allDevs.add(this);
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


    public void changeAdminDetails(){
        System.out.println("1.Change name");
        System.out.println("2.Change password");
        System.out.println("3.Return");
        String ans = getString();
        switch (ans){
            case "1":{
                System.out.println("Enter new name");
                String newName = getString();
                if (AdminMainPage.isValidAdminName(newName)){
                    this.setUsername(newName);
                }else{
                    System.out.println("This name is already taken.");
                }
                break;
            }
            case "2":{
                System.out.println("Enter new pass");
                this.setPassword(getString());
                break;
            }
            default:{
                break;
            }
        }
    }

    public void showProfile(){
        System.out.println("Name : "+ this.getUsername());
        System.out.println("Password : "+ this.getPassword());
        System.out.println("Roles : "+ this.getAllRoles());
        System.out.println("Enter anything to go back");
        getString();
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
}
