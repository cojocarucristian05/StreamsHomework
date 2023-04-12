package main;

import main.data.Customer;
import main.data.Order;
import main.data.Product;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<Customer> customerList = new ArrayList<>();
        List<Order> orderList = new ArrayList<>();
        List<Product> productList = new ArrayList<>();

        customerList.add(new Customer(1L, "Cristian", 1));
        customerList.add(new Customer(2L, "Andreea", 2));
        customerList.add(new Customer(3L, "Matei", 2));
        customerList.add(new Customer(4L, "Iulia", 1));

//        System.out.println(customerList);

        productList.add(new Product(1L, "Catedrala si Bazarul", "Books", 150.0));
        productList.add(new Product(2L, "Minge fotbal", "Toys", 50.0));
        productList.add(new Product(3L, "Eseu despre orbire", "Books", 90.0));
        productList.add(new Product(4L, "Racheta", "Toys", 40.0));
        productList.add(new Product(5L, "Nebunul", "Books", 10.0));
        productList.add(new Product(5L, "Sampon", "Baby", 300.0));


        HashSet<Product> products = new HashSet<>();
        products.add(new Product(10L, "Birou", "Casa", 350.0));


        orderList.add(new Order(1L, LocalDate.of(2021, 3, 14),
                LocalDate.of(2021, 3, 10), "in progress", new Customer(1L, "Cristian", 2), new HashSet<>(productList)));


        orderList.add(new Order(1L, LocalDate.of(2021, 7, 20),
                LocalDate.of(2021, 8, 25), "in progress", new Customer(1L, "Cristian", 2), new HashSet<>(productList)));

        orderList.add(new Order(1L, LocalDate.of(2021, 5, 21),
                LocalDate.of(2021, 7, 10), "done", new Customer(1L, "Cristian", 2), products));


        //ex1
        List<Product> expensiveBooks = productList
                .stream()
                .filter(product -> product.getCategory().equals("Books") && product.getPrice() > 100)
                .toList();

        System.out.println("\nex1: " + expensiveBooks + "\n");

        orderList.add(new Order(1L, LocalDate.of(2020, 1, 8),
                LocalDate.of(2020, 1, 10), "in progress", new Customer(1L, "Cristian", 2), new HashSet<>(expensiveBooks)));


        //ex2
        List<Order> orderThatContainsBabyProduct = orderList
                .stream()
                .filter(order -> order.getProducts()
                        .stream()
                        .anyMatch(product -> product.getCategory().equals("Baby"))
                )
                .toList();

        System.out.println("\nex2: " + orderThatContainsBabyProduct + "\n");


        //ex3
        double discount = 0.1;
        List<Product> toysList = productList
                .stream()
                .filter(product -> product.getCategory().equals("Toys"))
                .peek(product -> product.setPrice(product.getPrice() * (1 - discount)))
                .toList();

        System.out.println("\nex3: " + toysList + "\n");


        //ex4
        List<Product> productsOrderedByCustomerOfTier2 = orderList
                .stream()
                .filter(order -> (order.getCustomer().getTier() == 2
                        && order.getOrderDate().isAfter(LocalDate.of(2021, 2, 1))
                        && order.getOrderDate().isBefore(LocalDate.of(2021, 4, 1))))
                .flatMap(order -> order.getProducts().stream())
                .toList();

        System.out.println("\nex4: " + productsOrderedByCustomerOfTier2 + "\n");


        //ex5
        Product cheapestBook = productList
                .stream()
                .filter(product -> product.getCategory().equals("Books"))
                .min(Comparator.comparing(Product::getPrice))
                .orElse(null);

        System.out.println("\nex5: " + cheapestBook + "\n");


        //ex6
        List<Order> threeMostRecentOrder = orderList
                .stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .limit(3)
                .toList();

        System.out.println("\nex6: " + threeMostRecentOrder + "\n");


        //ex7
        Order highestTotalPriceOrder = orderList
                .stream()
                .max(Comparator.comparing(Order::totalPrice))
                .get();

        System.out.println("\nex7: " + highestTotalPriceOrder.totalPrice() + "\n");


        //ex8
        orderList.add(new Order(10L, LocalDate.of(2021, 3, 14), LocalDate.of(2021, 3, 18), "done", new Customer(23L, "Andrei", 2), new HashSet<>(expensiveBooks)));
        List<Order> orderOn14March2021 = orderList
                .stream()
                .filter(order -> order.getOrderDate().equals(LocalDate.of(2021, 3, 14)))
                .toList();

        double averagePayment = orderList
                .stream()
                .filter(order -> order.getOrderDate().equals(LocalDate.of(2021, 3, 14)))
                .mapToDouble(Order::totalPrice)
                .average()
                .getAsDouble();

//        System.out.println("\nex8: " + averagePayment);
        System.out.println("\nex8: " + orderOn14March2021 + "\n");


        //ex9
        String category = "House";
        Optional<Product> expensiveProduct = productList
                .stream()
                .filter(product -> product.getCategory().equals(category))
                .max(Comparator.comparing(Product::getPrice));

        if (expensiveProduct.isPresent()) {
            System.out.println("\nex9: " + expensiveProduct.get());
        } else {
            System.out.println("\nex9: No product found!");
        }


        //ex10
        Product mostOrderedProduct = orderList.stream()
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        System.out.println("\nex10: " + mostOrderedProduct);
    }
}
