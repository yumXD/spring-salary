package com.salary.controller;

import com.salary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class UserViewController {
    private final UserService userService;

    @GetMapping("/users/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/users/profile")
    public String myProfile(Model model, Principal principal) {
        // 현재 로그인한 사용자의 ID 가져오기
        String email = principal.getName();
        Long userId = userService.findByEmail(email);
        model.addAttribute("userId", userId);

        return "user/profile";  // profile.html 페이지로 이동
    }
}