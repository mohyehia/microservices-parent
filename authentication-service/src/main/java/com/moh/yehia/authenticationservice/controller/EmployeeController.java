package com.moh.yehia.authenticationservice.controller;

import com.moh.yehia.authenticationservice.model.Employee;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@Log4j2
public class EmployeeController {
    @GetMapping
    public ResponseEntity<List<Employee>> findAll() {
        log.info("EmployeeController :: findAll :: start");
        return new ResponseEntity<>(Arrays.asList(
                new Employee(1, "Mohamed", "Software Development"),
                new Employee(2, "Ahmed", "Information Technology")
        ), HttpStatus.OK);
    }
}
