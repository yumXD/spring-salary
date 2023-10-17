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
public class WorkDetailViewController {
    @GetMapping("/employee/{id}/work-detail")
    public String employeeDtl(@PathVariable("id") Long id, Model model) {
        log.info("근무표 페이지");
        model.addAttribute("id", id);
        return "work/workDetail";
    }
}
