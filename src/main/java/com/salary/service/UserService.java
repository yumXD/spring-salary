package com.salary.service;

import com.salary.domain.User;
import com.salary.dto.UserRequest;
import com.salary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public User createUser(UserRequest request) {
        validateDuplicateEmail(request.getEmail());

        User user = User.createUser(request, bCryptPasswordEncoder);

        return userRepository.save(user);
    }

    private void validateDuplicateEmail(String email) {
        User findUser = userRepository.findByEmail(email);
        if (findUser != null) {
            throw new IllegalStateException("이미 가입된 이메일입니다.");
        }
    }
}
