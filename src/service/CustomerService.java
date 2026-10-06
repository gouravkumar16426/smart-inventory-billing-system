package service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import interfaces.IService;
import model.Customer;
import exception.ResourceNotFoundException;

public class CustomerService implements IService<Customer> {

    private List<Customer> customerList = new ArrayList<>();

    @Override
    public void add(Customer customer) {
        // Simple check if ID already exists
        if (customerList.stream().anyMatch(c -> c.getId() == customer.getId())) {
            throw new IllegalArgumentException("Customer ID already exists.");
        }
        customerList.add(customer);
        System.out.println("Customer Added Successfully...");
    }

    @Override
    public void update(Customer customer) {
        Customer existing = search(customer.getId());
        if (existing != null) {
            existing.setName(customer.getName());
            existing.setPhone(customer.getPhone());
            System.out.println("Customer Updated Successfully...");
        } else {
            throw new ResourceNotFoundException("Customer Not Found.");
        }
    }

    @Override
    public void delete(int id) {
        boolean removed = customerList.removeIf(c -> c.getId() == id);
        if (removed) {
            System.out.println("Customer Deleted Successfully...");
        } else {
            throw new ResourceNotFoundException("Customer Not Found.");
        }
    }

    @Override
    public List<Customer> viewAll() {
        return customerList;
    }

    @Override
    public Customer search(int id) {
        return customerList.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Customer Data Not Found."));
    }

    public int totalCustomers() {
        return customerList.size();
    }

    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(customerList);
        } catch (IOException e) {
            System.out.println("Error saving customers: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists())
            return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            customerList = (List<Customer>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading customers: " + e.getMessage());
        }
    }
}