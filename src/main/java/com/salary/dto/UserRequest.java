package com.salary.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserRequest {
    @NotBlank(message = "사용자 이름은 필수 항목입니다.")
    private String username;

    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Email(message = "유효한 이메일 주소 형식이어야 합니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;
}
