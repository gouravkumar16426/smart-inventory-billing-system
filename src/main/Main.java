package main;

import java.util.Scanner;
import model.Customer;
import model.Product;
import service.CustomerService;
import service.LoginService;
import service.OrderService;
import service.ProductService;
import service.ReportService;
import exception.InvalidDataException;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ProductService productService = new ProductService();
        CustomerService customerService = new CustomerService();
        OrderService orderService = new OrderService(productService, customerService);
        ReportService reportService = new ReportService();
        LoginService loginService = new LoginService();

        // Data Serialization
        productService.loadFromFile("products.dat");
        customerService.loadFromFile("customers.dat");
        orderService.loadFromFile("orders.dat");

        System.out.println(" SMART INVENTORY & BILLING SYSTEM");
        System.out.print("Enter Username : ");
        String user = sc.next();
        System.out.print("Enter Password : ");
        String pass = sc.next();

        if (!loginService.login(user, pass)) {
            System.out.println("Invalid Username or Password");
            sc.close();
            return;
        }
        System.out.println("\nLogin Successful!");

        int choice;
        do {
            System.out.println("\n========== DASHBOARD ==========");
            System.out.println("1. Product Management");
            System.out.println("2. Customer Management");
            System.out.println("3. Order Management");
            System.out.println("4. Reports");
            System.out.println("5. Exit");
            System.out.print("Enter Choice : ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    int pchoice;
                    do {
                        System.out.println("\n------ PRODUCT MENU ------");
                        System.out.println("1. Add Product");
                        System.out.println("2. View All Products");
                        System.out.println("3. Search Product by ID");
                        System.out.println("4. Search Product by Name");
                        System.out.println("5. Update Product");
                        System.out.println("6. Delete Product");
                        System.out.println("7. Sort Products");
                        System.out.println("8. Low Stock Report");
                        System.out.println("9. Back");
                        System.out.print("Enter Choice : ");
                        pchoice = sc.nextInt();

                        try {
                            switch (pchoice) {
                                case 1:
                                    System.out.print("Enter Product ID : ");
                                    int pid = sc.nextInt();
                                    sc.nextLine();
                                    System.out.print("Enter Product Name : ");
                                    String pname = sc.nextLine();
                                    System.out.print("Enter Price : ");
                                    double price = sc.nextDouble();
                                    System.out.print("Enter Stock : ");
                                    int stock = sc.nextInt();
                                    productService.add(new Product(pid, pname, price, stock));
                                    break;
                                case 2:
                                    productService.viewAll().forEach(System.out::println);
                                    break;
                                case 3:
                                    System.out.print("Enter Product ID : ");
                                    System.out.println(productService.search(sc.nextInt()));
                                    break;
                                case 4:
                                    System.out.print("Enter Product Name : ");
                                    sc.nextLine();
                                    productService.searchProductByName(sc.nextLine());
                                    break;
                                case 5:
                                    System.out.print("Enter Product ID to Update : ");
                                    int updateId = sc.nextInt();
                                    sc.nextLine();
                                    System.out.print("Enter New Name : ");
                                    String upname = sc.nextLine();
                                    System.out.print("Enter New Price : ");
                                    double uprice = sc.nextDouble();
                                    System.out.print("Enter New Stock : ");
                                    int ustock = sc.nextInt();
                                    productService.update(new Product(updateId, upname, uprice, ustock));
                                    break;
                                case 6:
                                    System.out.print("Enter Product ID : ");
                                    productService.delete(sc.nextInt());
                                    break;
                                case 7:
                                    System.out.print("Sort By (name/price/stock) : ");
                                    sc.nextLine();
                                    productService.sortProducts(sc.nextLine());
                                    break;
                                case 8:
                                    System.out.print("Enter threshold (e.g. 5) : ");
                                    productService.getLowStockProducts(sc.nextInt());
                                    break;
                                case 9:
                                    break;
                                default:
                                    System.out.println("Invalid Choice");
                            }
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    } while (pchoice != 9);
                    break;
                case 2:
                    int cchoice;
                    do {
                        System.out.println("\n------ CUSTOMER MENU ------");
                        System.out.println("1. Add Customer");
                        System.out.println("2. View Customers");
                        System.out.println("3. Search Customer");
                        System.out.println("4. Update Customer");
                        System.out.println("5. Delete Customer");
                        System.out.println("6. Back");
                        System.out.print("Enter Choice : ");
                        cchoice = sc.nextInt();

                        try {
                            switch (cchoice) {
                                case 1:
                                    System.out.print("Enter Customer ID : ");
                                    int cid = sc.nextInt();
                                    sc.nextLine();
                                    System.out.print("Enter Customer Name : ");
                                    String cname = sc.nextLine();
                                    System.out.print("Enter Customer Phone : ");
                                    String phone = sc.nextLine();
                                    customerService.add(new Customer(cid, cname, phone));
                                    break;
                                case 2:
                                    customerService.viewAll().forEach(System.out::println);
                                    break;
                                case 3:
                                    System.out.print("Enter Customer ID : ");
                                    System.out.println(customerService.search(sc.nextInt()));
                                    break;
                                case 4:
                                    System.out.print("Enter Customer ID to Update : ");
                                    int updateCId = sc.nextInt();
                                    sc.nextLine();
                                    System.out.print("Enter New Name : ");
                                    String nName = sc.nextLine();
                                    System.out.print("Enter New Phone : ");
                                    String nPhone = sc.nextLine();
                                    customerService.update(new Customer(updateCId, nName, nPhone));
                                    break;
                                case 5:
                                    System.out.print("Enter Customer ID : ");
                                    customerService.delete(sc.nextInt());
                                    break;
                                case 6:
                                    break;
                                default:
                                    System.out.println("Invalid Choice");
                            }
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    } while (cchoice != 6);
                    break;
                case 3:
                    int ochoice;
                    do {
                        System.out.println("\n------ ORDER MENU ------");
                        System.out.println("1. Create Order");
                        System.out.println("2. View Orders");
                        System.out.println("3. Search Order");
                        System.out.println("4. View Customer Purchase History");
                        System.out.println("5. Back");
                        System.out.print("Enter Choice : ");
                        ochoice = sc.nextInt();

                        try {
                            switch (ochoice) {
                                case 1:
                                    System.out.print("Enter Order ID : ");
                                    int oid = sc.nextInt();
                                    System.out.print("Enter Customer ID : ");
                                    int cid = sc.nextInt();
                                    System.out.print("Enter Product ID : ");
                                    int pid = sc.nextInt();
                                    System.out.print("Enter Quantity : ");
                                    int qty = sc.nextInt();

                                    orderService.createOrder(oid, cid, pid, qty);
                                    break;
                                case 2:
                                    orderService.viewAll().forEach(System.out::println);
                                    break;
                                case 3:
                                    System.out.print("Enter Order ID : ");
                                    System.out.println(orderService.search(sc.nextInt()));
                                    break;
                                case 4:
                                    System.out.print("Enter Customer ID : ");
                                    orderService.searchCustomerPurchaseHistory(sc.nextInt());
                                    break;
                                case 5:
                                    break;
                                default:
                                    System.out.println("Invalid Choice");
                            }
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    } while (ochoice != 5);
                    break;
                case 4:
                    reportService.showReport(productService, customerService, orderService);
                    break;
                case 5:
                    System.out.println("\nSaving Data...");
                    productService.saveToFile("products.dat");
                    customerService.saveToFile("customers.dat");
                    orderService.saveToFile("orders.dat");
                    System.out.println("Thank You for Using Smart Inventory & Billing System");
                    System.out.println("Program Closed Successfully.");
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        } while (choice != 5);
        sc.close();
    }
}