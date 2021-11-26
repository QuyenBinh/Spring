package com.example.Response;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
@Component
public class JwtResponse {
    private String token;
    private final String type = "Bearer";
    private long id;
    private String username;
    private String email;
    private List<String> roles;
    public JwtResponse(String jwt, Long id, String name,String email, List<String> roles)  {
        this.token = jwt;
        this.id = id;
        this.username = name;
        this.email = email;
        this.roles = roles;
    }
}
