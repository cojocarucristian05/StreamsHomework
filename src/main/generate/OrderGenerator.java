package main.generate;

import main.data.Customer;
import main.data.Order;
import main.data.Product;

import java.time.LocalDate;
import java.util.*;

public class OrderGenerator {

    private static final String[] STATUSES = {"Pending", "Processing", "Shipped", "Delivered", "Cancelled"};

    public static List<Order> generateOrders(List<Customer> customers, List<Product> products, int numOrders) {
        List<Order> orders = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < numOrders; i++) {
            Long id = (long) (i + 1);
            LocalDate orderDate = LocalDate.now().minusDays(rand.nextInt(30));
            LocalDate deliveryDate = orderDate.plusDays(rand.nextInt(10) + 1);
            String status = STATUSES[rand.nextInt(STATUSES.length)];
            Customer customer = customers.get(rand.nextInt(customers.size()));
            Set<Product> orderProducts = new HashSet<>();
            int numOrderProducts = rand.nextInt(5) + 1;
            for (int j = 0; j < numOrderProducts; j++) {
                Product product = products.get(rand.nextInt(products.size()));
                orderProducts.add(product);
            }
            Order order = new Order(id, orderDate, deliveryDate, status, customer, orderProducts);
            orders.add(order);
        }
        return orders;
    }
}
