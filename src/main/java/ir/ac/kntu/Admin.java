package ir.ac.kntu;

import static ir.ac.kntu.AdminMainPage.*;

public class Admin {
    private String roles = "";

    private String username;

    private String password;

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

    public void addDeveloperRole(){
        this.roles += " Developer ";
        allDevs.add(this);
    }

    public void addSellerRole(){
        this.roles += " Seller ";
        allSellers.add(this);
    }

    public void addMainRole(){
        this.roles += "Seller Developer Main";
        allSellers.add(this);
        allDevs.add(this);
    }

    public boolean isMainAdmin(){
        if (this.roles.contains("Main")){
            return true;
        }
        return false;
    }

    public void removeDeveloperRole(){
        this.setRoles(this.getRoles().replaceAll("Developer",""));
    }

    public void removeSellerRole(){
        this.setRoles(this.getRoles().replaceAll("Seller",""));
    }

    public boolean isDeveloper(){
        return(this.getRoles().contains("Developer"));
    }

    public boolean isSeller(){
        return(this.getRoles().contains("Seller"));
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
}
