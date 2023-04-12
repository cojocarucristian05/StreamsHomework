package main;

import main.data.Customer;
import main.data.Order;
import main.data.Product;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static main.generate.CustomerGenerator.generateCustomers;
import static main.generate.ProductGenerator.generateProducts;
import static main.generate.OrderGenerator.generateOrders;

public class Main2 {
    public static void main(String[] args) {

        List<Customer> customerList = generateCustomers(100);
        List<Product> productList = generateProducts(100);
        List<Order> orderList = generateOrders(customerList, productList, 100);

        System.out.println(customerList);
        System.out.println(productList);
        System.out.println(orderList);

        //ex1
        List<Product> expensiveBooks = productList
                .stream()
                .filter(product -> product.getCategory().equals("Books") && product.getPrice() > 100)
                .toList();

        System.out.println("\nex1: " + expensiveBooks + "\n");


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

//        System.out.println("ex8: " + averagePayment);
        System.out.println("\nex8: " + orderOn14March2021 + "\n");


        //ex9
        String category = "House";
        Optional<Product> expensiveProduct = productList
                .stream()
                .filter(product -> product.getCategory().equals(category))
                .max(Comparator.comparing(Product::getPrice));

        if (expensiveProduct.isPresent()) {
            System.out.println("\nex9: " + expensiveProduct.get() + "\n");
        } else {
            System.out.println("\nex9: No product found!\n");
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
