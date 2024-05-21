package com.example.lumel.controller;

import com.example.lumel.service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CSVController {

    @Autowired
    private CSVService CSVService;

    @GetMapping("/")
    public String test() {
        return "Hello, Lumel!";
    }

    @GetMapping("/loadCSV")
    public ResponseEntity<String> loadCSV() {
        String status = CSVService.loadCSV();

        ResponseEntity<String> response;
        if ("SUCCESS".equals(status) || "PARTIAL".equals(status)) {
            response = new ResponseEntity<>(status, HttpStatusCode.valueOf(200));
        } else {
            response = new ResponseEntity<>(status, HttpStatusCode.valueOf(500));
        }

        return response;
    }
}
