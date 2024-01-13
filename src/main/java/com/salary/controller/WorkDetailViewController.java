package com.salary.controller;

import com.salary.service.WorkDetailService;
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

    private final WorkDetailService workDetailService;

    @GetMapping("/employee/{id}/work-detail")
    public String employeeDtl(@PathVariable("id") Long id, Model model) {
        log.info("근무표 페이지");
        if (workDetailService.findByEmployeeId(id) == null) {
            workDetailService.createWorkDetail(id,9_860L);
        }
        model.addAttribute("id", id);
        return "work/workDetail";
    }
}
