package com.salary.controller;

import com.salary.domain.User;
import com.salary.dto.UserRequest;
import com.salary.dto.UserResponse;
import com.salary.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

    // 모든 회원 조회
    @GetMapping("/api/users")
    public ResponseEntity<List<UserResponse>> findAllUsers() {
        List<UserResponse> users = userService.findAll()
                .stream()
                .map(UserResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok()
                .body(users);
    }

    // 특정 회원 조회
    @GetMapping("/api/users/{id}")
    public ResponseEntity<UserResponse> findUser(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok()
                .body(new UserResponse(user));
    }

    // 특정 회원 수정 (비밀번호 만)
    @PutMapping("/api/users/{id}")
    public ResponseEntity<User> updateUser(@Valid @RequestBody UserRequest request, @PathVariable("id") Long id) {
        User updatedUser = userService.update(id, request);
        return ResponseEntity.ok()
                .body(updatedUser);
    }

    // 특정 회원 삭제
    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.ok()
                .build();
    }
}
