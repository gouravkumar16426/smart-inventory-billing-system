package service;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import interfaces.IService;
import model.Product;
import exception.ResourceNotFoundException;

public class ProductService implements IService<Product> {

    private List<Product> productList = new ArrayList<>();

    @Override
    public void add(Product product) {
        if (productList.stream().anyMatch(p -> p.getId() == product.getId())) {
            throw new IllegalArgumentException("Product ID already exists.");
        }
        productList.add(product);
        System.out.println("Product Added Successfully...");
    }

    @Override
    public void update(Product product) {
        Product existing = search(product.getId());
        if (existing != null) {
            existing.setName(product.getName());
            existing.setPrice(product.getPrice());
            existing.setStock(product.getStock());
            System.out.println("Product Updated Successfully...");
        } else {
            throw new ResourceNotFoundException("Product Not Found.");
        }
    }

    @Override
    public void delete(int id) {
        boolean removed = productList.removeIf(p -> p.getId() == id);
        if (removed) {
            System.out.println("Product Deleted Successfully...");
        } else {
            throw new ResourceNotFoundException("Product Not Found.");
        }
    }

    @Override
    public List<Product> viewAll() {
        return productList;
    }

    @Override
    public Product search(int id) {
        return productList.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product Data Not Found."));
    }

    public void searchProductByName(String name) {
        List<Product> found = productList.stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        if (found.isEmpty()) {
            System.out.println("No products found with name: " + name);
        } else {
            found.forEach(System.out::println);
        }
    }

    public void sortProducts(String criteria) {
        if (productList.isEmpty()) {
            System.out.println("No Products Available to sort.");
            return;
        }

        List<Product> sortedList;
        switch (criteria.toLowerCase()) {
            case "name":
                sortedList = productList.stream().sorted(Comparator.comparing(Product::getName))
                        .collect(Collectors.toList());
                break;
            case "price":
                sortedList = productList.stream().sorted(Comparator.comparing(Product::getPrice))
                        .collect(Collectors.toList());
                break;
            case "stock":
                sortedList = productList.stream().sorted(Comparator.comparing(Product::getStock))
                        .collect(Collectors.toList());
                break;
            default:
                System.out.println("Invalid sort criteria.");
                return;
        }

        System.out.println("Products sorted by " + criteria + ":");
        sortedList.forEach(System.out::println);
    }

    public void getLowStockProducts(int threshold) {
        List<Product> lowStock = productList.stream()
                .filter(p -> p.getStock() < threshold)
                .collect(Collectors.toList());

        if (lowStock.isEmpty()) {
            System.out.println("No low stock products found.");
        } else {
            System.out.println("---- Low Stock Products ----");
            lowStock.forEach(System.out::println);
        }
    }

    public int totalProducts() {
        return productList.size();
    }

    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(productList);
        } catch (IOException e) {
            System.out.println("Error saving products: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists())
            return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            productList = (List<Product>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
    }
}