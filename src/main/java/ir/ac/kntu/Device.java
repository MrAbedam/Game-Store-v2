package ir.ac.kntu;

public class Device extends Item {

    private int supplyNumber;

    private Admin seller;

    public Admin getSeller() {
        return seller;
    }

    public void setSeller(Admin seller) {
        seller = seller;
    }

    public boolean isPartOfSellTeam(Admin admin) {
        if (admin.isMainAdmin() || admin == getSeller()) {
            return true;
        }
        return false;
    }

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
