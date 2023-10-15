package com.salary.exception;

import com.salary.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionViewHandler {

    @ExceptionHandler(TypeMismatchException.class)
    public String handleEntityNotFoundException(TypeMismatchException ex, Model model) {
        log.error("TypeMismatchException 발생");
        model.addAttribute("errorMessage", "잘못된 입력 값입니다: " + ex.getValue());
        return "error/error";
    }

    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseBody
    public ResponseEntity<?> handleDuplicateEmailException(DuplicateEmailException ex) {
        log.error("이메일 중복 예외 발생");
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
