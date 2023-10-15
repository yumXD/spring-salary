package com.salary.controller;

import com.salary.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

/**
 * 사용자 관련 뷰를 처리하는 컨트롤러
 */
@RequiredArgsConstructor
@Controller
@Slf4j
public class UserViewController {
    private final UserService userService;

    @GetMapping("/users/login")
    public String login() {
        log.info("로그인 페이지");
        return "user/login";
    }

    @GetMapping("/users/register")
    public String register() {
        log.info("회원가입 페이지");
        return "user/register";
    }

    @GetMapping("/users/profile")
    public String myProfile(Model model, Principal principal) {
        log.info("내 정보 페이지");
        // 현재 로그인한 사용자의 ID 가져오기
        String email = principal.getName();
        Long userId = userService.findByEmail(email);
        model.addAttribute("userId", userId);

        return "user/profile";
    }

    @PreAuthorize("#id == authentication.principal.id")
    @GetMapping("/users/profile/edit/{id}")
    public String editUserProfile(Model model, @PathVariable("id") Long id) {
        log.info("회원 수정 페이지");
        model.addAttribute("id", id);
        return "user/register";
    }
}
