import java.util.ArrayList;
import java.util.List;

public class Checkout {
    public static void process(Customer customer, Cart cart) {
        if (cart.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        double subtotal = 0;
        List<Shipable> itemsToShip = new ArrayList<>();

        // Validate cart items and calculate subtotal
        for (cartItem item : cart.getItems()) {
            Product product = item.product;

            if (item.quantity > product.getQuantity()) {
                throw new RuntimeException(product.getName() + " is out of stock");
            }

            if (product.isExpired()) {
                throw new RuntimeException(product.getName() + " is expired");
            }

            subtotal += item.getTotalPrice();

            // Add each shippable unit separately
            if (product.isShippable()) {
                for (int i = 0; i < item.quantity; i++) {
                    itemsToShip.add((Shipable) product);
                }
            }
        }

        double shippingCost = itemsToShip.isEmpty() ? 0 : 30;
        double totalCost = subtotal + shippingCost;

        if (!customer.canAfford(totalCost)) {
            throw new RuntimeException("Insufficient balance");
        }

        // Ship items if needed
        if (!itemsToShip.isEmpty()) {
           Service.ship(itemsToShip);
        }

        // Print receipt
        System.out.println("** Checkout receipt **");
        for (cartItem item : cart.getItems()) {
            System.out.printf("%dx %s %.0f%n", item.quantity, item.product.getName(), item.getTotalPrice());
            item.product.reduceQuantity(item.quantity);
        }

        System.out.println("----------------------");
        System.out.printf("Subtotal %.0f%n", subtotal);
        System.out.printf("Shipping %.0f%n", shippingCost);
        System.out.printf("Amount %.0f%n", totalCost);

        customer.deduct(totalCost);
        System.out.printf("Remaining balance: %.0f%n", customer.getBalance());
    }

}
