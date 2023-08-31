package com.salary.controller;

import com.salary.domain.User;
import com.salary.dto.UserRequest;
import com.salary.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;

    // 회원 가입 처리
    @PostMapping("/api/users")
    public ResponseEntity<User> register(@Valid @RequestBody UserRequest request) {
        User savedUser = userService.createUser(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedUser);
    }
}
