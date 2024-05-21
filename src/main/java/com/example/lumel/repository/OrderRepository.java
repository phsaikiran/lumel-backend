package com.example.lumel.repository;

import com.example.lumel.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    @Query("SELECT COUNT(distinct c.id) from Customer c, Order o WHERE c.id = o.customer.id AND :startDate <= o.dateOfSale AND :endDate >= o.dateOfSale")
    Integer getCustomerIdBetweenDateOfSale(LocalDate startDate, LocalDate endDate);

    @Query("SELECT COUNT(o.id) FROM Order o WHERE :startDate <= o.dateOfSale AND :endDate >= o.dateOfSale")
    Integer getOrderCountBetweenDateOfSale(LocalDate startDate, LocalDate endDate);
}