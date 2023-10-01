package com.salary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class WorkDetailViewController {
    @GetMapping("/employee/{id}/work-detail")
    public String employeeDtl(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        return "work/workDetail";
    }
}
