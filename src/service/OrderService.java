package service;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import interfaces.IService;
import model.Order;
import model.Product;
import model.Customer;
import exception.ResourceNotFoundException;
import exception.InsufficientStockException;
import exception.InvalidDataException;

public class OrderService implements IService<Order> {

    private List<Order> orderList = new ArrayList<>();

    private ProductService productService;
    private CustomerService customerService;

    // Constructor to link services
    public OrderService(ProductService productService, CustomerService customerService) {
        this.productService = productService;
        this.customerService = customerService;
    }

    // Default constructor for cases when services are linked later
    public OrderService() {
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void createOrder(int orderId, int customerId, int productId, int qty) throws InvalidDataException {
        if (orderList.stream().anyMatch(o -> o.getOrderId() == orderId)) {
            throw new InvalidDataException("Order ID already exists.");
        }

        Customer customer;
        try {
            customer = customerService.search(customerId);
        } catch (ResourceNotFoundException e) {
            throw new InvalidDataException("Customer not found.");
        }

        Product product;
        try {
            product = productService.search(productId);
        } catch (ResourceNotFoundException e) {
            throw new InvalidDataException("Product not found.");
        }

        if (product.getStock() < qty) {
            throw new InsufficientStockException("Not enough stock available. Current stock: " + product.getStock());
        }

        // Deduct stock
        product.setStock(product.getStock() - qty);

        double totalPrice = product.getPrice() * qty;

        Order newOrder = new Order(
                orderId,
                customer.getId(),
                customer.getName(),
                product.getId(),
                product.getName(),
                qty,
                totalPrice,
                LocalDate.now());

        add(newOrder);
    }

    @Override
    public void add(Order order) {
        orderList.add(order);
        System.out.println("Order Created Successfully...");
        System.out.println("---- INVOICE ----");
        System.out.println(order);
    }

    @Override
    public void update(Order order) {
        throw new UnsupportedOperationException("Updating orders is not currently supported.");
    }

    @Override
    public void delete(int id) {
        boolean removed = orderList.removeIf(o -> o.getOrderId() == id);
        if (removed) {
            System.out.println("Order Deleted Successfully...");
        } else {
            throw new ResourceNotFoundException("Order Not Found.");
        }
    }

    @Override
    public List<Order> viewAll() {
        return orderList;
    }

    @Override
    public Order search(int id) {
        return orderList.stream()
                .filter(o -> o.getOrderId() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Order Data Not Found."));
    }

    public int totalOrders() {
        return orderList.size();
    }

    public double getTotalRevenue() {
        return orderList.stream()
                .mapToDouble(Order::getTotalPrice)
                .sum();
    }

    public void searchCustomerPurchaseHistory(int customerId) {
        List<Order> history = orderList.stream()
                .filter(o -> o.getCustomerId() == customerId)
                .collect(Collectors.toList());

        if (history.isEmpty()) {
            System.out.println("No purchase history found for Customer ID: " + customerId);
        } else {
            System.out.println("---- Purchase History for Customer ID " + customerId + " ----");
            history.forEach(System.out::println);
        }
    }

    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(orderList);
        } catch (IOException e) {
            System.out.println("Error saving orders: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists())
            return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            orderList = (List<Order>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading orders: " + e.getMessage());
        }
    }
}