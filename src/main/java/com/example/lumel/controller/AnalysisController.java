package com.example.lumel.controller;

import com.example.lumel.dto.AverageResponse;
import com.example.lumel.dto.TotalCustomerResponse;
import com.example.lumel.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class AnalysisController {

    @Autowired
    private AnalysisService analysisService;

    @GetMapping("/totalCustomers")
    public ResponseEntity<TotalCustomerResponse> totalCustomers(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate) {
        TotalCustomerResponse response = new TotalCustomerResponse();

        if (startDate == null || endDate == null) {
            response.setError("Start date or end date is null");
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(400));
        }

        if (endDate.isBefore(startDate)) {
            response.setError("End date is before start date");
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(400));
        }

        int count = analysisService.totalCustomersBetweenDateOfSale(startDate, endDate);
        response.setCount(count);
        response.setError("");

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/totalOrders")
    public ResponseEntity<TotalCustomerResponse> totalOrders(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate) {
        TotalCustomerResponse response = new TotalCustomerResponse();

        if (startDate == null || endDate == null) {
            response.setError("Start date or end date is null");
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(400));
        }

        if (endDate.isBefore(startDate)) {
            response.setError("End date is before start date");
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(400));
        }

        int count = analysisService.getOrderCountBetweenDateOfSale(startDate, endDate);
        response.setCount(count);
        response.setError("");

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/averageOrderValue")
    public ResponseEntity<AverageResponse> averageOrderValue(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate) {
        AverageResponse response = new AverageResponse();

        if (startDate == null || endDate == null) {
            response.setError("Start date or end date is null");
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(400));
        }

        if (endDate.isBefore(startDate)) {
            response.setError("End date is before start date");
            return new ResponseEntity<>(response, HttpStatusCode.valueOf(400));
        }

        float average = analysisService.getAverageOrderValue(startDate, endDate);
        response.setAverage(average);
        response.setError("");

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(200));
    }
}
