package com.salary.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionViewHandler {

    @ExceptionHandler(TypeMismatchException.class)
    public String handleEntityNotFoundException(TypeMismatchException ex, Model model) {
        log.info("TypeMismatchException 발생");
        model.addAttribute("errorMessage", "잘못된 입력 값입니다: " + ex.getValue());
        return "error/error";
    }
}
