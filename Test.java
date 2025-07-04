import java.util.Calendar;
import java.util.Date;

public class Test {

        public static void main(String[] args) {
            try {
                // Setup reusable products
                Calendar cal = Calendar.getInstance();
                Date futureDate = new Date(cal.getTimeInMillis() + 24 * 60 * 60 * 1000); // tomorrow
                Date pastDate = new Date(cal.getTimeInMillis() - 24 * 60 * 60 * 1000);   // yesterday

                ExpirableProduct cheese = new ExpirableProduct("Cheese", 100, 2, futureDate, 0.2);
                ExpirableProduct expiredBiscuits = new ExpirableProduct("Expired Biscuits", 100, 5, pastDate, 0.7);
                notExpireable tv = new notExpireable ("TV", 500, 1, true, 3.0);
                notExpireable scratchCard = new notExpireable("Scratch Card", 50, 10, false, 0);

                // 1. Add product within available quantity
                try {
                    Cart cart = new Cart();
                    cart.add(cheese, 2); // within stock
                    System.out.println(" Add within stock: Success");
                } catch (Exception e) {
                    System.out.println(" Add within stock: Failed - " + e.getMessage());
                }

                // 2. Add more than available stock
                try {
                    Cart cart = new Cart();
                    cart.add(tv, 2); // Only 1 in stock
                    System.out.println(" Over stock: Unexpected success");
                } catch (Exception e) {
                    System.out.println("Over stock: Caught error - " + e.getMessage());
                }

                // 3. Add expired product
                try {
                    Cart cart = new Cart();
                    cart.add(expiredBiscuits, 1);
                    Customer customer = new Customer("Ali", 500);
                    Checkout.process(customer, cart);
                    System.out.println(" Expired product: Unexpected success");
                } catch (Exception e) {
                    System.out.println(" Expired product: Caught error - " + e.getMessage());
                }

                // 4. Checkout with empty cart
                try {
                    Cart cart = new Cart();
                    Customer customer = new Customer("Ali", 500);
                    Checkout.process(customer, cart);
                    System.out.println(" Empty cart: Unexpected success");
                } catch (Exception e) {
                    System.out.println(" Empty cart: Caught error - " + e.getMessage());
                }

                // 5. Customer with insufficient balance
                try {
                    Cart cart = new Cart();
                    cart.add(tv, 1); // TV = 500
                    Customer poorCustomer = new Customer("Poor Guy", 400); // Not enough
                    Checkout.process(poorCustomer, cart);
                    System.out.println(" Insufficient balance: Unexpected success");
                } catch (Exception e) {
                    System.out.println(" Insufficient balance: Caught error - " + e.getMessage());
                }

                // 6. Product goes out of stock
                try {
                    Cart cart1 = new Cart();
                    cart1.add(tv, 1); // TV has quantity 1
                    Customer c1 = new Customer("C1", 1000);
                    Checkout.process(c1, cart1); // should succeed

                    // Now try to buy another TV after it's out of stock
                    Cart cart2 = new Cart();
                    cart2.add(tv, 1); // no stock left
                    Customer c2 = new Customer("C2", 1000);
                    Checkout.process(c2, cart2);
                    System.out.println(" Out of stock after checkout: Unexpected success");
                } catch (Exception e) {
                    System.out.println("Out of stock after checkout: Caught error - " + e.getMessage());
                }

                // 7. Successful checkout with shipment and receipt
                try {
                    Customer richCustomer = new Customer("Rich", 1000);
                    Cart cart = new Cart();
                    cart.add(cheese, 1);       // shippable + not expired
                    cart.add(scratchCard, 1);  // not shippable

                    Checkout.process(richCustomer, cart);
                    System.out.println(" Successful checkout: Passed");
                } catch (Exception e) {
                    System.out.println(" Successful checkout: Failed - " + e.getMessage());
                }

            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }


