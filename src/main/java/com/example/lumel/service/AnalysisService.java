package com.example.lumel.service;

import com.example.lumel.entity.Order;
import com.example.lumel.entity.Product;
import com.example.lumel.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AnalysisService {
    @Autowired
    private OrderRepository orderRepository;

    public int totalCustomersBetweenDateOfSale(LocalDate startDate, LocalDate endDate) {
        return orderRepository.getCustomerIdBetweenDateOfSale(startDate, endDate);
    }

    public int getOrderCountBetweenDateOfSale(LocalDate startDate, LocalDate endDate) {
        return orderRepository.getOrderCountBetweenDateOfSale(startDate, endDate);
    }

    public float getAverageOrderValue(LocalDate startDate, LocalDate endDate) {
        List<Order> orderList = orderRepository.getOrderBetweenDateOfSale(startDate, endDate);
        float total = 0.0F;
        for (Order order : orderList) {
            Product product = order.getProduct();
            float orderValue = order.getQuantitySold() * product.getUnitPrice() * (1 - product.getDiscount()) + order.getShippingCost();
            total += orderValue;
        }

        return total / (float) orderList.size();
    }
}
