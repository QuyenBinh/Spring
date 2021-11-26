package com.example.Request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class LoginRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    @NotEmpty(message = "Thiếu username")
    private String username;
    @NotBlank
    @Size(min = 5,max = 30)
    @NotEmpty(message = "Thiếu mật khẩu")
    private String password;
}
