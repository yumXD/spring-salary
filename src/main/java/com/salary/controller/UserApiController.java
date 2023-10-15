package com.salary.controller;

import com.salary.domain.User;
import com.salary.dto.UserRequest;
import com.salary.dto.UserResponse;
import com.salary.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserApiController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> register(@Valid @RequestBody UserRequest request) {
        User savedUser = userService.createUser(request);
        log.info("{} 님 회원가입 완료", savedUser.getActualUsername());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedUser);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAllUsers() {
        List<UserResponse> users = userService.findAll()
                .stream()
                .map(UserResponse::new).collect(Collectors.toList());
        log.info("모든 회원 조회 완료");
        return ResponseEntity.ok()
                .body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUser(@PathVariable("id") Long id) {
        User user = userService.findById(id);
        log.info("{} 님 정보 조회완료", user.getActualUsername());
        return ResponseEntity.ok()
                .body(new UserResponse(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@Valid @RequestBody UserRequest request, @PathVariable("id") Long id) {
        User updatedUser = userService.update(id, request);
        log.info("{} 님 정보 수정완료 (비밀번호 만)", updatedUser.getActualUsername());
        return ResponseEntity.ok()
                .body(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        log.info("{} 회원 삭제완료", id);
        return ResponseEntity.ok()
                .build();
    }
}
