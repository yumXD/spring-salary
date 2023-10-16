package com.salary.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
@Slf4j
public class EmployeeViewController {
    @GetMapping("/employees")
    public String employees() {
        log.info("전체 직원 조회 페이지");
        return "employee/employeeMng";
    }

    @GetMapping("/employee/new")
    public String register() {
        log.info("직원 추가 페이지");
        return "employee/register";
    }

    @GetMapping("/employee/{id}")
    public String employeeDtl(@PathVariable("id") Long id, Model model) {
        log.info("{} 직원 상세 페이지", id);
        model.addAttribute("id", id);
        return "employee/employeeDtl";
    }

    @GetMapping("/employee/edit/{id}")
    public String updateEmployee(@PathVariable("id") Long id, Model model) {
        log.info("{} 직원 수정 페이지", id);
        model.addAttribute("id", id);
        return "employee/register";
    }
}
