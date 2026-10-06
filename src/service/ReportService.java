package service;

public class ReportService {

        public void showReport(ProductService productService,
                        CustomerService customerService,
                        OrderService orderService) {

                System.out.println("\n========== REPORT ==========");

                System.out.println("Total Products : "
                                + productService.totalProducts());

                System.out.println("Total Customers : "
                                + customerService.totalCustomers());

                System.out.println("Total Orders : "
                                + orderService.totalOrders());

                System.out.println("Total Revenue : $"
                                + orderService.getTotalRevenue());

                System.out.println("============================");
        }
}