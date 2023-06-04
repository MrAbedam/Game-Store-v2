package ir.ac.kntu;

import static ir.ac.kntu.AdminDeviceList.changeMonitorDetails;
import static ir.ac.kntu.Get.getInt;
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
        AdminGameList.listOfDevices.add(this);

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

    public int numberOfOwnedMonitors(User user){
        int num = 0;
        for (Monitor testMonitor: user.getOwnedMonitors() ){
            if (testMonitor == this){
                num++;
            }
        }
        return num;
    }

    public void showMonitorDetails(User user) {
        System.out.println("Here are the details of the mentioned monitor:");
        System.out.println("Monitor name: " + this.getName());
        System.out.println("Monitor description: " + this.getDescription());
        System.out.println("Monitor update rate: " +this.getUpdateRate());
        System.out.println("Monitor X and Y: "+this.getxSize()+" "+this.getySize());
        System.out.println("Monitor price: " + this.getPrice() + "$");
        if (user.doesUserOwn(this)) {
            System.out.println(Colors.green + "Number of owned: "+ this.numberOfOwnedMonitors(user) + Colors.reset);
        }
            System.out.println(Colors.red + "Not owned" + Colors.reset);
            System.out.println(Colors.blue +"Enter 'BUY' to buy this Monitor, enter anything else to go back."+Colors.reset);
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

    public void changeUpdateRate(Admin admin){
        System.out.println("Current update rate: " + this.getUpdateRate());
        System.out.println("Enter new update rate:");
        int newUpdateRate = getInt();
        this.setUpdateRate(newUpdateRate);
        System.out.println("update rate changed!");
        changeMonitorDetails(this, admin);
    }

    public void changexSize(Admin admin){
        System.out.println("Current xSize: " + this.getxSize());
        System.out.println("Enter new xSize:");
        int newxSize = getInt();
        this.setxSize(newxSize);
        System.out.println("update xSize!");
        changeMonitorDetails(this, admin);
    }

    public void changeySize(Admin admin){
        System.out.println("Current ySize: " + this.getySize());
        System.out.println("Enter new ySize:");
        int newySize = getInt();
        this.setySize(newySize);
        System.out.println("update ySize!");
        changeMonitorDetails(this, admin);
    }

    public void changeResponseTime(Admin admin){
        System.out.println("Current response time: " + this.getResponseTime());
        System.out.println("Enter new response time:");
        int newResponseTime = getInt();
        this.setResponseTime(newResponseTime);
        System.out.println("response time changed!");
        changeMonitorDetails(this, admin);
    }

    public void changeName(Admin admin){
        System.out.println("Current name: " + this.getName());
        System.out.println("Enter new name:");
        String newName = getString();
        this.setName(newName);
        System.out.println("Name changed!");
        changeMonitorDetails(this, admin);
    }

    public void changeDescription(Admin admin){
        System.out.println("Current description: " + this.getDescription());
        System.out.println("Enter new description:");
        String newDescription = getString();
        this.setDescription(newDescription);
        System.out.println("Description changed!");
        changeMonitorDetails(this, admin);
    }

    public void changeSupplyNumber(Admin admin){
        System.out.println("Current supply number: " + this.getSupplyNumber());
        System.out.println("Enter new supply number:");
        int newSupplyNumber = getInt();
        this.setSupplyNumber(newSupplyNumber);
        System.out.println("Supply number changed!");
        changeMonitorDetails(this, admin);
    }

    public void changePrice(Admin admin){
        System.out.println("Current Price: " + this.getPrice());
        System.out.println("Enter new price:");
        int newPrice = getInt();
        this.setPrice(newPrice);
        System.out.println("Price changed!");
        changeMonitorDetails(this, admin);
    }
}
