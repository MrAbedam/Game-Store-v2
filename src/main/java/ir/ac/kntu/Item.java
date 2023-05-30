package ir.ac.kntu;

import java.util.ArrayList;
import java.util.HashMap;

import static ir.ac.kntu.Colors.blue;
import static ir.ac.kntu.Colors.reset;

public class Item {

    String name;

    String description;

    double price;

    double avgRate;

    HashMap<User, Double> rateUser = new HashMap<>();

    ArrayList<String> reviews = new ArrayList<>();

    public void addReview(String review, User user){
        String finalReview = user.getUserName() + ": "+ review;
        this.reviews.add(finalReview);
    }

    public void showReviews(){
        for(String testReview : this.reviews){
            System.out.println(blue + testReview + reset);
        }
    }

    public void addRate(User user, double rate) {
        this.rateUser.put(user, rate);
    }

    public void updateRate() {
        double sum = 0;
        int numberOfUsers = 0;
        for (double testRate : this.rateUser.values()) {
            sum += testRate;
            numberOfUsers++;
        }
        this.setAvgRate(sum / numberOfUsers);
    }

    public double getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(double avgRate) {
        this.avgRate = avgRate;
    }

    public Item(String name, String description, double price){
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public void showLibraryItemDetails(User user){
        if (this instanceof Game){
            ((Game)this).showLibraryGameDetails(user);
        }
        else if(this instanceof Monitor){
            ((Monitor)this).showLibraryMonitorDetails(user);
        }
        else if (this instanceof Controller){
            ((Controller)this).showLibraryControllerDetails(user);
        }
    }

    public void showItemDetails(User user){
        if (this instanceof Game){
            ((Game)this).showGameDetails(user);
        }
        else if(this instanceof Monitor){
            ((Monitor)this).showMonitorDetails(user);
        }
        else if (this instanceof Controller){
            ((Controller)this).showControllerDetails(user);
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
