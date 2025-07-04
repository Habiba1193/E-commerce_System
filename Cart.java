import java.util.*;
public class Cart {
    List<cartItem> items = new ArrayList<>();

    public void add(Product product, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        if (quantity > product.getQuantity()) throw new RuntimeException("Not enough in stock for " + product.getName());
        items.add(new cartItem(product, quantity));
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public List<cartItem> getItems() {
        return items;
    }
}
