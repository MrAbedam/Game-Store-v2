package ir.ac.kntu;

import static ir.ac.kntu.Get.*;
import static ir.ac.kntu.StoreProgram.makeHashie;

public class Game extends Item{

    String genre;



    public Game(String name, String description,String genre, double price) {
        super(name, description, price);
        this.genre= genre;
        AdminGameList.listOfItems.add(this);
        AdminGameList.listOfGames.add(this);

    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }



    public void changeGameDetailOptions(){
        System.out.println("Which detail do you want to change?");
        System.out.println("1.Name");
        System.out.println("2.Description");
        System.out.println("3.Genre");
        System.out.println("4.Price");
        System.out.println("5.Return");
        makeHashie();
    }

    public void changeGameName(){
        System.out.println("Current name: " + this.getName());
        System.out.println("Enter new name:");
        String newName = getString();
        this.setName(newName);
        System.out.println("Name changed!");
        makeHashie();
        this.changeGameDetail();
    }

    public void changeGameDetail() {
        changeGameDetailOptions();
        int detailNumber = getInt();
        switch (detailNumber) {
            case 1: {
                changeGameName();
                break;
            }
            case 2: {
                System.out.println("Current description: " + this.getDescription());
                System.out.println("Enter new description:");
                String newDescription = getString();
                this.setDescription(newDescription);
                System.out.println("Description changed!");
                makeHashie();
                this.changeGameDetail();
                break;
            }
            case 3: {
                System.out.println("Current genre: " + this.getGenre());
                System.out.println("Enter new genre:");
                String newGenre = getString();
                this.setGenre(newGenre);
                System.out.println("Genre changed!");
                makeHashie();
                this.changeGameDetail();
                break;
            }
            case 4: {
                System.out.println("Current price: " + this.getPrice());
                System.out.println("Enter new price:");
                double newPrice = getDouble();
                this.setPrice(newPrice);
                System.out.println("Price changed!");
                makeHashie();
                this.changeGameDetail();
                break;
            }
            case 5: {
                AdminGameList.adminGameListMenu();
                break;
            }
            default: {
                System.out.println("Wrong input, redirecting to start of page.");
                this.changeGameDetail();
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
        if (user.doesUserOwn(this)) {
            System.out.println(Colors.green + "Owned" + Colors.reset);
        }
        System.out.println("Press Anything to go to community menu, or press 'q' to go back.");
        String ans = getString();
        switch (ans){
            case "q":{
                LibraryOptions.libraryMenu(user);
                break;
            }
            default:{
                LibraryOptions.gameCommunityAndRate(this,user);
                break;
            }
        }
    }
}
