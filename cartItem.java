public class cartItem {
    Product product;
    int quantity;

    public cartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return quantity * product.getPrice();
    }
}
