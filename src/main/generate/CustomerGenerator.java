package main.generate;

import main.data.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomerGenerator {

    private static final String[] NAMES = {"Alice", "Bob", "Charlie", "David", "Emma", "Frank", "Grace", "Henry", "Isabella", "James"};
    private static final int[] TIERS = {1, 2, 3};

    public static List<Customer> generateCustomers(int numCustomers) {
        List<Customer> customers = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < numCustomers; i++) {
            Long id = (long) (i + 1);
            String name = NAMES[rand.nextInt(NAMES.length)];
            Integer tier = TIERS[rand.nextInt(TIERS.length)];
            Customer customer = new Customer(id, name, tier);
            customers.add(customer);
        }
        return customers;
    }
}