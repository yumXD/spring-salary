package com.salary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class EmployeeViewController {
    @GetMapping("/employees")
    public String employees() {
        return "employee/employeeMng";
    }

    @GetMapping("/employee/new")
    public String register() {
        return "employee/register";
    }

    @GetMapping("/employee/{id}")
    public String employeeDtl(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        return "employee/employeeDtl";
    }
}
