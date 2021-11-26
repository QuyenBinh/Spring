package com.example.Request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.implementation.bind.annotation.Empty;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class SinupRequest {

    @NotBlank
    @Size(min = 3, max = 20)
    @NotEmpty(message = "Thiếu username")
    private String username;
    @NotBlank
    @Size(min = 5,max = 30)
    @NotEmpty(message = "Thiếu mật khẩu")
    private String password;
    @NotBlank
    @Size(min = 6, max = 30)
    @Email(message  = "Định dạnh email chưa đung")
    private String email;
    private Set<String> role;

}
