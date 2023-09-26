package com.salary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class EmployeeViewController {
    @GetMapping("/employees")
    public String employees() {
        return "employee/employeeMng";
    }
}
