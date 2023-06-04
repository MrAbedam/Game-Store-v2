package ir.ac.kntu;

import java.util.ArrayList;

import static ir.ac.kntu.AdminGameList.listOfDevices;
import static ir.ac.kntu.AdminGameList.removeItem;
import static ir.ac.kntu.Get.*;
import static ir.ac.kntu.StoreProgram.makeHashie;

public class AdminDeviceList {

    public static void adminDeviceListMenuOptions() {
        System.out.println("Admins gameList page.");
        System.out.println("1.Add a Device.");
        System.out.println("2.Change a device's details.");
        System.out.println("3.Remove a device.");
        System.out.println("4.Show all of devices.");
        System.out.println("5.Return.");
    }

    public static void makeDevice(Admin admin) {
        System.out.println("choose a device to make:");
        System.out.println("1.Make a monitor");
        System.out.println("2.Make a controller");
        System.out.println("3.Return");
        String ans = getString();
        switch (ans) {
            case "1": {
                makeMonitor();
                adminDeviceListMenu(admin);
                break;
            }
            case "2": {
                makeController();
                adminDeviceListMenu(admin);
                break;
            }
            case "3": {
                adminDeviceListMenu(admin);
                break;
            }
            default: {
                System.out.println("Wrong input, redirecting to start of page");
                makeDevice(admin);
            }
        }
    }

    public static void makeMonitor() {
        System.out.println("Enter monitor's name:");
        String name = getString();
        System.out.println("Enter monitor's description");
        String description = getString();
        System.out.println("Enter monitor's price");
        double price = getDouble();
        System.out.println("Enter number of supply");
        int supplyNumber = getInt();
        System.out.println("Enter update Rate");
        int updateRate = getInt();
        System.out.println("Enter xSize");
        int xSize = getInt();
        System.out.println("Enter ySize");
        int ySize = getInt();
        System.out.println("Enter response time");
        int responseTime = getInt();
        Monitor newMonitor = new Monitor(name, description, price, supplyNumber, updateRate, xSize, ySize, responseTime);
        System.out.println("Monitor added!");
    }


    public static void makeController() {
        System.out.println("Enter controller's name:");
        String name = getString();
        System.out.println("Enter controller's description");
        String description = getString();
        System.out.println("Enter controller's price");
        double price = getDouble();
        System.out.println("Enter number of supply");
        int supplyNumber = getInt();
        System.out.println("Enter compatible console");
        String console = getString();
        boolean doesHaveWire;
        System.out.println("Does controller have wire? Enter y for yes and anything else for no");
        String ans = getString();
        if (ans.equals("y") || ans.equals("Y")) {
            doesHaveWire = true;
        } else {
            doesHaveWire = false;
        }
        Controller newController = new Controller(name, description, price, supplyNumber, console, doesHaveWire);
        System.out.println("Monitor added!");
    }

    public static void adminDeviceChangeDetails(Admin admin) {
        System.out.println("Enter device name to change details:");
        String ans = getString();
        ArrayList<Device> filteredList = findDeviceByName(ans);
        if (filteredList.isEmpty()) {
            System.out.println("No Devices Matched. Enter anything to return to Admins DeviceList.");
            getString();
            adminDeviceListMenu(admin);
        } else {
            System.out.println("Choose a device between the filtered devices:");
            showGivenListOfDevices(filteredList);
            int deviceChoice = getInt();
            Device chosenDevice = filteredList.get((deviceChoice - 1) % filteredList.size());
            changeDeviceDetails(chosenDevice, admin);
        }
    }


    public static void changeDeviceDetails(Device device, Admin admin) {
        if (device instanceof Monitor) {
            changeMonitorDetails((Monitor) device, admin);
        } else {
            changeControllerDetails((Controller) device, admin);
        }
    }


    public static void changeControllerDetails(Controller controller, Admin admin) {
        changeControllerDetailOptions();
        String ans = getString();
        switch (ans) {
            case "1": {
                controller.changeName(admin);
                break;
            }
            case "2": {
                controller.changeDescription(admin);
                break;
            }
            case "3": {
                controller.changeSupplyNumber(admin);
                break;
            }
            case "4": {
                controller.changePrice(admin);
                break;
            }
            case "5": {
                controller.changeConsole(admin);
                break;
            }
            case "6": {
                controller.setDoesHaveWire(!controller.getDoesHaveWire());
                changeControllerDetails(controller, admin);
                break;
            }
            case "7": {
                adminDeviceListMenu(admin);
            }
            default: {
                changeControllerDetails(controller, admin);
            }
        }
    }

    public static void changeControllerDetailOptions() {
        System.out.println("Which detail do you want to change?");
        System.out.println("1.Name");
        System.out.println("2.Description");
        System.out.println("3.Supply Number");
        System.out.println("4.Price");
        System.out.println("5.Console");
        System.out.println("6.flip wire state");
        System.out.println("7.Return");
    }


    public static void changeMonitorDetails(Monitor monitor, Admin admin) {
        changeMonitorDetailOptions();
        String ans = getString();
        switch (ans) {
            case "1": {
                monitor.changeName(admin);
                break;
            }
            case "2": {
                monitor.changeDescription(admin);
                break;
            }
            case "3": {
                monitor.changeSupplyNumber(admin);
                break;
            }
            case "4": {
                monitor.changePrice(admin);
                break;
            }
            case "5": {
                monitor.changexSize(admin);
                break;
            }
            case "6": {
                monitor.changeySize(admin);
                break;
            }
            case "7": {
                monitor.changeResponseTime(admin);
                break;
            }
            case "8": {
                monitor.changeUpdateRate(admin);
                break;
            }
            case "9": {
                adminDeviceListMenu(admin);
            }
            default: {
                changeMonitorDetails(monitor, admin);
            }
        }
    }


    public static void changeMonitorDetailOptions() {
        System.out.println("Which detail do you want to change?");
        System.out.println("1.Name");
        System.out.println("2.Description");
        System.out.println("3.Supply number");
        System.out.println("4.Price");
        System.out.println("5.xSize");
        System.out.println("6.ySize");
        System.out.println("7.Response time");
        System.out.println("8.Update rate");
        System.out.println("9.Return");
        System.out.println("Enter choice:");
    }

    public static void adminDeviceListMenu(Admin admin) {
        adminDeviceListMenuOptions();
        String ans = getString();
        switch (ans) {
            case "1": {
                makeDevice(admin);
                break;
            }
            case "2": {
                adminDeviceChangeDetails(admin);
                break;
            }
            case "3": {
                System.out.println("Enter game's name.");
                String filterName = getString();
                ArrayList<Device> filteredList = findDeviceByName(filterName);
                if (filteredList.isEmpty()) {
                    System.out.println("No Devices Matched. Enter anything to return to Admins DeviceList.");
                    getString();
                    adminDeviceListMenu(admin);
                } else {
                    System.out.println("Choose a device between the filtered devices:");
                    showGivenListOfDevices(filteredList);
                    int deviceChoice = getInt();
                    Device chosenDevice = filteredList.get((deviceChoice - 1) % filteredList.size());
                    removeItem(chosenDevice);
                    adminDeviceListMenu(admin);
                }
                break;
            }
            case "4": {
                showGivenListOfDevices(listOfDevices);
                System.out.println("Enter anything to return to Admins DeviceList.");
                String tmp = getString();
                makeHashie();
                adminDeviceListMenu(admin);
                break;
            }
            case "5": {
                AdminMainPage.displayAdminPage(admin);
                break;
            }
            default: {
                System.out.println("Wrong input, redirecting to start of page.");
                adminDeviceListMenu(admin);
                break;
            }
        }
    }

    public static ArrayList<Device> findDeviceByName(String deviceName) {
        ArrayList<Device> filteredDevices = new ArrayList<>();
        for (Device foundDevice : listOfDevices) {
            if (foundDevice.getName().equals(deviceName)) {
                filteredDevices.add(foundDevice);
            }
        }
        return filteredDevices;
    }

    public static void showGivenListOfDevices(ArrayList<Device> listOfGivenDevices) {
        int deviceCounter = 1;
        for (Device game : listOfGivenDevices) {
            System.out.println(deviceCounter + ". " + game.getName() + " => " + game.getPrice() + "$ ");
            deviceCounter++;
        }
    }
}
