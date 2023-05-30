package ir.ac.kntu;

import static ir.ac.kntu.Get.getString;

public class Controller extends Device{

    private String console;

    private boolean doesHaveWire;

    public Controller(String name, String description, double price, int supplyNumber,String console,boolean doesHaveWire) {
        super(name, description, price, supplyNumber);
        this.console = console;
        this.doesHaveWire = doesHaveWire;
        AdminGameList.listOfItems.add(this);
        AdminGameList.listOfControllers.add(this);
    }

    public boolean getDoesHaveWire() {
        return doesHaveWire;
    }

    public void showLibraryControllerDetails(User user) {
        System.out.println("Here are the details of the mentioned controller:");
        System.out.println("Controller name: " + this.getName());
        System.out.println("Controller description: " + this.getDescription());
        System.out.println("Controller console: " +this.getConsole());
        System.out.println("Wireless: "+this.getDoesHaveWire());
        System.out.println("Controller price: " + this.getPrice() + "$");
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

    public int numberOfOwnedControllers(User user){
        int num = 0;
        for (Controller testController: user.getOwnedControllers() ){
            if (testController == this){
                num++;
            }
        }
        return num;
    }

    public void showControllerDetails(User user) {
        System.out.println("Here are the details of the mentioned controller:");
        System.out.println("Controller name: " + this.getName());
        System.out.println("Controller description: " + this.getDescription());
        System.out.println("Controller console: " +this.getConsole());
        System.out.println("Wireless: "+this.getDoesHaveWire());
        System.out.println("Controller price: " + this.getPrice() + "$");
        if (user.doesUserOwn(this)) {
            System.out.println(Colors.green + "Number of owned: "+ this.numberOfOwnedControllers(user) + Colors.reset);
        }
            System.out.println(Colors.blue +"Enter 'BUY' to buy this Controller, enter anything else to go back."+Colors.reset);
            String ans = getString();
            ans = ans.toUpperCase();
            switch (ans) {
                case "BUY": {
                    boolean didBuy = user.buyController(this);
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

    public void setDoesHaveWire(boolean doesHaveWire) {
        this.doesHaveWire = doesHaveWire;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }
}
