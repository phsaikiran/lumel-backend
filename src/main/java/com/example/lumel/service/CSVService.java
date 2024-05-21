package com.example.lumel.service;

import com.example.lumel.entity.Customer;
import com.example.lumel.entity.Order;
import com.example.lumel.entity.Product;
import com.example.lumel.repository.CustomerRepository;
import com.example.lumel.repository.OrderRepository;
import com.example.lumel.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class CSVService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.DAYS)
    private void scheduledLoadCSV() {
        this.loadCSV();
    }

    public String loadCSV() {
        String file = "/sample.csv";
        InputStream csvStream = this.getClass().getResourceAsStream(file);
        if (csvStream == null) {
            System.out.println("File not found");
            return "ERROR";
        }

        String status = "SUCCESS";
        try (BufferedReader br = new BufferedReader(new InputStreamReader(csvStream))) {
            // Skip the first line
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                String error = this.validate(values);
                if (!"".equals(error)) {
                    System.out.println("Skipping line " + line);
                    System.out.println(error);
                    status = "PARTIAL";
                    continue;
                }

                String orderId = values[0];
                String productId = values[1];
                String customerId = values[2];
                String productName = values[3];
                String category = values[4];
                String region = values[5];
                LocalDate dateOfSale = LocalDate.parse(values[6]);
                int quantitySold = Integer.parseInt(values[7]);
                float unitPrice = Float.parseFloat(values[8]);
                float discount = Float.parseFloat(values[9]);
                float shippingCost = Float.parseFloat(values[10]);
                String paymentMethod = values[11];
                String customerName = values[12];
                String customerEmail = values[13];
                String customerAddress = values[14];

                try {
                    String customerStatus = this.handleCustomer(customerId, customerName, customerEmail, customerAddress);
                    System.out.println("Customer with ID " + customerId + " was handled with a status " + customerStatus);

                    String productStatus = this.handleProduct(productId, productName, category, unitPrice, discount);
                    System.out.println("Product with ID " + productId + " was handled with a status " + productStatus);

                    String orderStatus = this.handleOrder(orderId, productId, customerId, dateOfSale, quantitySold, shippingCost, paymentMethod, region);
                    System.out.println("Order with ID " + orderId + " was handled with a status " + orderStatus);
                } catch (Exception e) {
                    System.out.println("Failed handling line " + line);
                    System.out.println(e.getMessage());
                    status = "PARTIAL";
                    continue;
                }
            }
        } catch (IOException e) {
            System.out.println("File not found");
            status = "ERROR";
        }

        return status;
    }

    public String validate(String[] values) {
        if (values.length != 15) {
            return "Wrong count of values";
        }

        String orderId = values[0];
        if ("".equals(orderId)) {
            return "Empty order ID";
        }

        String productId = values[1];
        if ("".equals(productId)) {
            return "Empty productId";
        }

        String customerId = values[2];
        if ("".equals(customerId)) {
            return "Empty customerId";
        }

        String productName = values[3];
        if ("".equals(productName)) {
            return "Empty productName";
        }

        String category = values[4];
        if ("".equals(category)) {
            return "Empty category";
        }

        String region = values[5];

        LocalDate dateOfSale;
        try {
            dateOfSale = LocalDate.parse(values[6]);
        } catch (Exception e) {
            return "Failed to parse Date of sale";
        }


        int quantitySold;
        try {
            quantitySold = Integer.parseInt(values[7]);
        } catch (Exception e) {
            return "Failed to parse Quantity Sold";
        }

        float unitPrice;
        try {
            unitPrice = Float.parseFloat(values[8]);
        } catch (Exception e) {
            return "Failed to parse Unit price";
        }

        float discount;
        try {
            discount = Float.parseFloat(values[9]);
        } catch (Exception e) {
            return "Failed to parse discount";
        }

        float shippingCost;
        try {
            shippingCost = Float.parseFloat(values[10]);
        } catch (Exception e) {
            return "Failed to parse shipping cost";
        }

        String paymentMethod = values[11];
        if ("".equals(paymentMethod)) {
            return "Empty paymentMethod";
        }

        String customerName = values[12];
        if ("".equals(customerName)) {
            return "Empty customerName";
        }

        String customerEmail = values[13];
        if ("".equals(customerEmail)) {
            return "Empty customerEmail";
        }

        String customerAddress = values[14];
        if ("".equals(customerAddress)) {
            return "Empty customerAddress";
        }

        return "";
    }

    public String handleCustomer(String customerId, String customerName, String customerEmail, String customerAddress) {
        Optional<Customer> customerOp = customerRepository.findById(customerId);
        Customer customer;
        String status;
        if (customerOp.isEmpty()) {
            customer = new Customer();
            status = "NEW";
        } else {
            customer = customerOp.get();
            status = "MODIFY";
        }

        customer.setId(customerId);
        customer.setName(customerName);
        customer.setEmail(customerEmail);
        customer.setAddress(customerAddress);
        try {
            customerRepository.save(customer);
        } catch (Exception e) {
            System.out.println("Failed to save customer: " + e.getMessage());
            status = "FAIL";
        }

        return status;
    }

    public String handleProduct(String productId, String productName, String category, float unitPrice, float discount) {
        Optional<Product> productOp = productRepository.findById(productId);
        Product product;
        String status;
        if (productOp.isEmpty()) {
            product = new Product();
            status = "NEW";
        } else {
            product = productOp.get();
            status = "MODIFY";
        }

        product.setId(productId);
        product.setName(productName);
        product.setCategory(category);
        product.setUnitPrice(unitPrice);
        product.setDiscount(discount);
        try {
            productRepository.save(product);
        } catch (Exception e) {
            System.out.println("Failed to save product: " + e.getMessage());
            status = "FAIL";
        }

        return status;
    }

    public String handleOrder(String orderId, String productId, String customerId, LocalDate dateOfSale, int quantitySold, float shippingCost, String paymentMethod, String region) throws Exception {
        Optional<Order> orderOp = orderRepository.findById(orderId);
        Order order;
        String status;
        if (orderOp.isEmpty()) {
            order = new Order();
            status = "NEW";
        } else {
            order = orderOp.get();
            status = "MODIFY";
        }

        Optional<Product> productOp = productRepository.findById(productId);
        if (productOp.isEmpty()) {
            throw new Exception("Order handle failed. Product not present");
        }
        Optional<Customer> customerOp = customerRepository.findById(customerId);
        if (customerOp.isEmpty()) {
            throw new Exception("Order handle failed. Customer not present");
        }

        order.setId(orderId);
        order.setProduct(productOp.get());
        order.setCustomer(customerOp.get());
        order.setDateOfSale(dateOfSale);
        order.setQuantitySold(quantitySold);
        order.setShippingCost(shippingCost);
        order.setPaymentMethod(paymentMethod);
        order.setRegion(region);
        try {
            orderRepository.save(order);
        } catch (Exception e) {
            System.out.println("Failed to save order: " + e.getMessage());
            status = "FAIL";
        }

        return status;
    }
}
