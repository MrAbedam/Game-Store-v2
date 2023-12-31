package ir.ac.kntu.Products;

import ir.ac.kntu.AdminPages.Admin;
import ir.ac.kntu.AdminPages.AdminGameList;
import ir.ac.kntu.HelperClasses.Colors;
import ir.ac.kntu.UserPages.LibraryOptions;
import ir.ac.kntu.UserPages.StoreOptions;
import ir.ac.kntu.UserPages.User;

import java.util.ArrayList;

import static ir.ac.kntu.AdminPages.AdminGameList.*;
import static ir.ac.kntu.AdminPages.AdminMainPage.allAdmins;
import static ir.ac.kntu.HelperClasses.Get.*;
import static ir.ac.kntu.StoreProgram.makeHashie;

public class Game extends Item {

    private boolean isOutOfOrder;

    private String genre;

    private ArrayList<Admin> developers = new ArrayList<>();

    private Admin firstDev;

    private int level;

    private boolean isBeta;

    public Game(String name, String description, String genre, double price, int level, boolean isBeta, Admin admin) {
        super(name, description, price);
        this.genre = genre;
        this.level = level;
        this.isBeta = isBeta;
        AdminGameList.listOfItems.add(this);
        listOfGames.add(this);
        this.addDev(admin);
        this.firstDev = admin;
        for (Admin testAdmin : allAdmins) {
            if (testAdmin.isMainAdmin() && testAdmin!=firstDev) {
                this.addDev(testAdmin);
            }
        }
    }

    public void sendMessageToDev(User user) {
        System.out.println("Enter message:");
        String msg = Colors.blue + user.getUserName() + " thoughts on " + this.getName() + " : " + Colors.reset;
        msg = msg + getString();
        this.getFirstDev().getInbox().add(msg);
    }

    public ArrayList<Admin> getDevelopers() {
        return developers;
    }

    public void addDev(Admin admin) {
        if (!this.getDevelopers().contains(admin) && (admin.isDeveloper())){
            this.getDevelopers().add(admin);
        }
    }

    public boolean isPartOfTeam(Admin admin) {
        if (this.getDevelopers().contains(admin)) {
            return true;
        }
        return false;
    }

    public Admin getFirstDev() {
        return firstDev;
    }

    public void setFirstDev(Admin firstDev) {
        if (!this.getDevelopers().contains(firstDev)) {
            this.getDevelopers().add(firstDev);
        }
        this.firstDev = firstDev;
    }

    public boolean isBeta() {
        return isBeta;
    }

    public void setBeta(boolean beta) {
        isBeta = beta;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isOutOfOrder() {
        return isOutOfOrder;
    }

    public void setOutOfOrder(boolean working) {
        isOutOfOrder = working;
    }

    public void changeGameDetailOptions() {
        System.out.println("Which detail do you want to change?");
        System.out.println("1.Name");
        System.out.println("2.Description");
        System.out.println("3.Genre");
        System.out.println("4.Price");
        System.out.println("5.Return");
        makeHashie();
    }

    public void flipIsOutOfOrder() {
        this.setOutOfOrder(!this.isOutOfOrder());
        if (this.isOutOfOrder){
            listOfGames.remove(this);
            listOfItems.remove(this);
            outOfOrderGames.add(this);
        }else {
            listOfGames.add(this);
            listOfItems.add(this);
            outOfOrderGames.remove(this);
        }
    }

    public void changeGameName(Admin admin) {
        System.out.println("Current name: " + this.getName());
        System.out.println("Enter new name:");
        String newName = getString();
        this.setName(newName);
        System.out.println("Name changed!");
        makeHashie();
        this.changeGameDetail(admin);
    }

    public void changeGameDescription(Admin admin) {
        System.out.println("Current description: " + this.getDescription());
        System.out.println("Enter new description:");
        String newDescription = getString();
        this.setDescription(newDescription);
        System.out.println("Description changed!");
        makeHashie();
        this.changeGameDetail(admin);
    }

    public void changeGameDetail(Admin admin) {
        changeGameDetailOptions();
        int detailNumber = getInt();
        switch (detailNumber) {
            case 1: {
                changeGameName(admin);
                break;
            }
            case 2: {
                changeGameDescription(admin);
                break;
            }
            case 3: {
                System.out.println("Current genre: " + this.getGenre());
                System.out.println("Enter new genre:");
                String newGenre = getString();
                this.setGenre(newGenre);
                System.out.println("Genre changed!");
                makeHashie();
                this.changeGameDetail(admin);
                break;
            }
            case 4: {
                System.out.println("Current price: " + this.getPrice());
                System.out.println("Enter new price:");
                double newPrice = getDouble();
                this.setPrice(newPrice);
                System.out.println("Price changed!");
                makeHashie();
                this.changeGameDetail(admin);
                break;
            }
            case 5: {
                AdminGameList.adminGameListMenu(admin);
                break;
            }
            default: {
                System.out.println("Wrong input, redirecting to start of page.");
                this.changeGameDetail(admin);
                break;
            }
        }
    }

    public void showGameDetails(User user) {
        System.out.println("Here are the details of the mentioned game:");
        System.out.println("Game name: " + this.getName());
        System.out.println("Game description: " + this.getDescription());
        System.out.println("Game genre: " + this.getGenre());
        System.out.println("Game price: " + this.getPrice() + "$");
        System.out.println("Game level: " + this.getLevel());
        System.out.println("Game rate: " + this.getAvgRate());
        if (user.doesUserOwn(this)) {
            System.out.println(Colors.green + "Owned" + Colors.reset);
            System.out.println("Press Anything to go back to Store menu.");
            getString();
        } else {
            System.out.println(Colors.red + "Not owned" + Colors.reset);
            System.out.println("Enter 'BUY' to buy this game, enter anything else to go back.");
            String ans = getString();
            ans = ans.toUpperCase();
            switch (ans) {
                case "BUY": {
                    boolean didBuy = user.buyGame(this);
                    System.out.println("Press Anything to go back to Store menu.");
                    getString();
                    break;
                }
                default: {
                    StoreOptions.storeMenu(user);
                    break;
                }
            }
        }
    }

    public void showLibraryGameDetails(User user) {
        System.out.println("Here are the details of the mentioned game:");
        System.out.println("Game name: " + this.getName());
        System.out.println("Game description: " + this.getDescription());
        System.out.println("Game genre: " + this.getGenre());
        System.out.println("Game price: " + this.getPrice() + "$");
        System.out.println("Game level: " + this.getLevel());
        if (user.doesUserOwn(this)) {
            System.out.println(Colors.green + "Owned" + Colors.reset);
        }
        if (!this.isBeta) {
            System.out.println("Press Anything to go to community menu, or press 'q' to go back.");
            String ans = getString();
            switch (ans) {
                case "q": {
                    LibraryOptions.libraryMenu(user);
                    break;
                }
                default: {
                    LibraryOptions.gameCommunityAndRate(this, user);
                    break;
                }
            }
        } else {
            System.out.println("Press Anything to leave a feedback, or press 'q' to go back.");
            String ans = getString();
            if (!ans.equals("q")) {
                this.feedBack(user);
            }
            LibraryOptions.libraryMenu(user);
        }
    }

    public void feedBack(User user) {
        this.sendMessageToDev(user);
        System.out.println("Feedback sent.");
    }

}
