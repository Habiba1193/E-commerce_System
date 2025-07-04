public class notExpireable extends Product implements Shipable {
    private boolean shippable;
    private double weight;

    public notExpireable (String name, double price, int quantity, boolean shippable, double weight) {
        super(name, price, quantity);
        this.shippable = shippable;
        this.weight = weight;
    }

    public boolean isExpired() {
        return false;
    }

    public boolean isShippable() {
        return shippable;
    }

    public double getWeight() {
        return shippable ? weight : 0;
    }

}
