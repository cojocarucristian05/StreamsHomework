package main.generate;

import main.data.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProductGenerator {

    private static final String[] NAMES = {"Laptop", "Smartphone", "Tablet", "Headphones", "Speaker", "Camera", "TV", "Mouse", "Keyboard", "Monitor"};
    private static final String[] CATEGORIES = {"Electronics", "Home", "Clothing", "Books", "Beauty", "Sports", "Toys", "Music", "Food", "Pet Supplies"};

    public static List<Product> generateProducts(int numProducts) {
        List<Product> products = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < numProducts; i++) {
            Long id = (long) (i + 1);
            String name = NAMES[rand.nextInt(NAMES.length)];
            String category = CATEGORIES[rand.nextInt(CATEGORIES.length)];
            Double price = rand.nextDouble() * 1000;
            Product product = new Product(id, name, category, price);
            products.add(product);
        }
        return products;
    }
}