package com.example.lumel.service;

import com.example.lumel.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
}
