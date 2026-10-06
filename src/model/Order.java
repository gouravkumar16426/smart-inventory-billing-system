package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Order implements Serializable {

    private int orderId;
    private int customerId;
    private String customerName;
    private int productId;
    private String productName;
    private int quantity;
    private double totalPrice;
    private LocalDate orderDate;

    public Order(int orderId, int customerId, String customerName, int productId, String productName,
            int quantity, double totalPrice, LocalDate orderDate) {

        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order ID : " + orderId +
                "\nDate : " + orderDate +
                "\nCustomer ID : " + customerId + " (" + customerName + ")" +
                "\nProduct ID : " + productId + " (" + productName + ")" +
                "\nQuantity : " + quantity +
                "\nTotal Price : " + totalPrice +
                "\n-------------------------";
    }
}