package ir.ac.kntu;

import static ir.ac.kntu.Get.getString;

public class Monitor extends Device{

    private int updateRate;

    private int xSize;

    private int ySize;

    private int responseTime;


    public Monitor(String name, String description, double price, int supplyNumber,int updateRate, int xSize, int ySize, int responseTime) {
        super(name, description, price, supplyNumber);
        this.responseTime = responseTime;
        this.xSize = xSize;
        this.ySize = ySize;
        this. updateRate = updateRate;
        AdminGameList.listOfItems.add(this);
        AdminGameList.listOfMonitors.add(this);

    }

    public void showLibraryMonitorDetails(User user) {
        System.out.println("Here are the details of the mentioned monitor:");
        System.out.println("Monitor name: " + this.getName());
        System.out.println("Monitor description: " + this.getDescription());
        System.out.println("Monitor update rate: " +this.getUpdateRate());
        System.out.println("Monitor X and Y: "+this.getxSize()+" "+this.getySize());
        System.out.println("Monitor price: " + this.getPrice() + "$");
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

    public void showMonitorDetails(User user) {
        System.out.println("Here are the details of the mentioned monitor:");
        System.out.println("Monitor name: " + this.getName());
        System.out.println("Monitor description: " + this.getDescription());
        System.out.println("Monitor update rate: " +this.getUpdateRate());
        System.out.println("Monitor X and Y: "+this.getxSize()+" "+this.getySize());
        System.out.println("Monitor price: " + this.getPrice() + "$");
        if (user.doesUserOwn(this)) {
            System.out.println(Colors.green + "Owned" + Colors.reset);
            System.out.println("Press Anything to go back to Store menu.");
            getString();
        } else {
            System.out.println(Colors.red + "Not owned" + Colors.reset);
            System.out.println("Enter 'BUY' to buy this monitor, enter anything else to go back.");
            String ans = getString();
            ans = ans.toUpperCase();
            switch (ans) {
                case "BUY": {
                    boolean didBuy = user.buyMonitor(this);
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

    public int getUpdateRate() {
        return updateRate;
    }

    public void setUpdateRate(int updateRate) {
        this.updateRate = updateRate;
    }

    public int getxSize() {
        return xSize;
    }

    public void setxSize(int xSize) {
        this.xSize = xSize;
    }

    public int getySize() {
        return ySize;
    }

    public void setySize(int ySize) {
        this.ySize = ySize;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }
}
