import java.util.*;
class ExpirableProduct extends Product implements Shipable {
    private Date expiryDate;
    private double weight;

    public ExpirableProduct(String name, double price, int quantity, Date expiryDate, double weight) {
        super(name, price, quantity);
        this.expiryDate = expiryDate;
        this.weight = weight;
    }

    public boolean isExpired() {
        return new Date().after(expiryDate);
    }

    public boolean isShippable() {
        return true;
    }

    public double getWeight() {
        return weight;
    }
}