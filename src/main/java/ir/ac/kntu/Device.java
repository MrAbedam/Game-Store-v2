package ir.ac.kntu;

public class Device extends Item{

    private int supplyNumber;

    public Device(String name, String description, double price, int supplyNumber) {
        super(name, description, price);
        this.supplyNumber = supplyNumber;
    }

    public int getSupplyNumber() {
        return supplyNumber;
    }

    public void setSupplyNumber(int supplyNumber) {
        this.supplyNumber = supplyNumber;
    }

}
